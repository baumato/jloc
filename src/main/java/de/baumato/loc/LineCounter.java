package de.baumato.loc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.stream.Stream;

import de.baumato.loc.configuration.Configuration;
import de.baumato.loc.messages.Messages;
import de.baumato.loc.printer.ConsolePrinter;

public class LineCounter {

	private final Configuration conf;
	private final ConsolePrinter printer;

	public LineCounter(Configuration conf, ConsolePrinter printer) {
		this.conf = conf;
		this.printer = printer;
	}

	public long count() {
		printer.step(Messages.FILE.get() + ";" + Messages.COUNT.get());
		long sum = countLinesInDir(conf.getDirectory(), conf.getExcludeDirs());
		printer.step(Messages.SUM.get() + ";" + sum);
		return sum;
	}

	private long countLinesInDir(Path dir, Collection<String> excludeDirs) {
		try (Stream<Path> paths = Files.walk(dir)) {
			return paths.filter(p -> p.getFileName().toString().endsWith(".java"))
				.filter(p -> !pathContainsOneOfDirs(p, excludeDirs))
				.mapToLong(p -> countLinesInFile(p))
				.sum();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static boolean pathContainsOneOfDirs(Path p, Collection<String> excludeDirs) {
		for (int i = 0; i < p.getNameCount(); i++) {
			if (excludeDirs.contains(p.getName(i).toString())) {
				return true;
			}
		}
		return false;
	}

	private long countLinesInFile(Path p) {
		long count = countLinesInFile0(p);
		printer.step(p.toString() + ";" + count);
		return count;
	}

	@SuppressWarnings("squid:S2677") // the returned value from readLine should not be used here
	private static long countLinesInFile0(Path p) {
		/*
		 * We cannot use Files.lines because that uses BufferedReader with the default charset and this results
		 * to java.nio.charset.MalformedInputException when a file has not the default charset. We can use new
		 * Scanner(p) but the scanner is slower than using BufferedReader like below.
		 */
		try (BufferedReader reader = new BufferedReader(
			new InputStreamReader(Files.newInputStream(p, StandardOpenOption.READ)))) {
			int count = 0;
			while (reader.readLine() != null) {
				count++;
			}
			return count;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
