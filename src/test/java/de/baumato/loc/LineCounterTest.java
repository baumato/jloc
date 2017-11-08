package de.baumato.loc;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.javaparser.JavaParser;

public class LineCounterTest {

	private final Path unformatted = Paths.get("src/test/resources/SomeClass.unformatted.java");
	private final Path formatted = Paths.get("src/test/resources/SomeClass.formatted.java");

	@Test
	public void shouldReadJavaFileAfterApplyingDefaultFormatter() throws Exception {
		String expected = JavaParser.parse(unformatted).toString();
		String actual = new String(Files.readAllBytes(formatted));
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void shouldReadJavaFileAfterApplyingDefaultFormatter2() throws Exception {
		long uc = LineCounter.countLines(unformatted);
		long fc = LineCounter.countLines(formatted);
		assertThat(uc).isEqualTo(fc).isEqualTo(31);
	}
}
