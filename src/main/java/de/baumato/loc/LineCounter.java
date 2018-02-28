package de.baumato.loc;

import static de.baumato.loc.util.MorePaths.endsWithIgnoreCase;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.stream.Stream;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;

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
		long sum = countLinesInDir();
		printer.step(Messages.SUM.get() + ";" + sum);
		return sum;
	}

	private long countLinesInDir() {
		try (Stream<Path> paths = Files.walk(conf.getDirectory())) {
			return paths.filter(p -> endsWithIgnoreCase(p, ".java"))
					.filter(p -> !pathContainsOneOfDirs(p, conf.getExcludeDirs()))
					.mapToLong(this::countLinesInFile)
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
		long count = countLines(p);
		printer.step(p.toString() + ";" + count);
		return count;
	}

	long countLines(Path p) {
		/*
		 * We cannot use Files.lines because that uses BufferedReader with the default charset and this results to
		 * java.nio.charset.MalformedInputException when a file has not the default charset. We can use new
		 * Scanner(p) but the scanner is slower than using BufferedReader like below.
		 */
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(readFileNormalized(p)))) {
			int count = 0;
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) {
					if (countEmptyLines()) {
						count++;
					}
				} else {
					count++;
				}
			}
			return count;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private InputStream readFileNormalized(Path p) {
		try {
			// normalize java file by parsing it and then converting it to String
			CompilationUnit cu = JavaParser.parse(p);
			if (!countComments()) {
				removeAllComments(cu);
			}
			byte[] fileContent = cu.toString().getBytes();
			conf.getAppLogger().trace("{} has been normalized", p);
			return new ByteArrayInputStream(fileContent);
		} catch (IOException | ParseProblemException e) {
			conf.getAppLogger().error("Error during reading '{}'. Error was: {} => Fallback to simple counting.", p, e.getClass().getSimpleName());
			return readFile(p);
		}
	}

	private InputStream readFile(Path p) {
		try {
			return Files.newInputStream(p, StandardOpenOption.READ);
		} catch (IOException e) {
			throw new UncheckedIOException("Error during reading: " + p, e);
		}
	}

	private boolean countComments() {
		return conf.getCalculationMode().countComments;
	}

	private static void removeAllComments(CompilationUnit cu) {
		cu.getAllContainedComments().forEach(Comment::remove);
	}

	private boolean countEmptyLines() {
		return conf.getCalculationMode().countEmptyLines;
	}
}
