package de.baumato.loc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

import de.baumato.loc.configuration.Configuration;
import de.baumato.loc.printer.ConsolePrinter;

public class LineCounter {

	private final Configuration conf;
	private final ConsolePrinter printer;

	public LineCounter(Configuration conf, ConsolePrinter printer) {
		this.conf = conf;
		this.printer = printer;
	}

	public long count() {
		return countLinesInDir(conf.getDirectory(), conf.getExcludeDirs());
	}

	private long countLinesInDir(Path dir, Collection<String> excludeDirs) {
		try (Stream<Path> paths = Files.walk(dir)) {
			long sum = paths.filter(p -> p.getFileName().toString().endsWith(".java"))
				.filter(p -> !pathContainsOneOfDirs(p, excludeDirs))
				.peek(p -> printer.step(p.toString()))
				.mapToLong(p -> countLinesInFile(p))
				.sum();
			return sum;
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

	private static long countLinesInFile(Path p) {
		try (Stream<String> lines = Files.lines(p)) {
			return lines.count();
		} catch (IOException | RuntimeException e) {
			throw new RuntimeException("Error during processing: " + p, e);
		}
	}
}
