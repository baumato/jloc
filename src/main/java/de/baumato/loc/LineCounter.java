package de.baumato.loc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import de.baumato.loc.configuration.Configuration;

public class LineCounter {

	private final Configuration conf;

	public LineCounter(Configuration conf) {
		this.conf = conf;
	}

	public long count() {
		return countLinesInDir(conf.getDirectory());
	}

	private static long countLinesInDir(Path dir) {
		try (Stream<Path> paths = Files.walk(dir)) {
			long sum = paths.filter(p -> p.getFileName().toString().endsWith(".java"))
				.mapToLong(p -> countLinesInFile(p))
				.sum();
			return sum;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static long countLinesInFile(Path p) {
		try (Stream<String> lines = Files.lines(p)) {
			return lines.count();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
