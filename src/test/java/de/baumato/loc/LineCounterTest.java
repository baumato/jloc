package de.baumato.loc;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.JavaParser;

import de.baumato.loc.configuration.Configuration;
import de.baumato.loc.printer.ConsolePrinter;

public class LineCounterTest {

	private final Path unformatted = Paths.get("src/test/resources/SomeClass.unformatted.java");
	private final Path formatted = Paths.get("src/test/resources/SomeClass.formatted.java");

	private LineCounter lc;

	@Before
	public void setup() {
		lc = new LineCounter(new Configuration(), new ConsolePrinter());
	}

	@Test
	public void shouldReadJavaFileAfterApplyingDefaultFormatter() throws Exception {
		String expected = JavaParser.parse(unformatted).toString();
		String actual = new String(Files.readAllBytes(formatted));
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void shouldReadJavaFileAfterApplyingDefaultFormatter2() throws Exception {
		long uc = lc.countLines(unformatted);
		long fc = lc.countLines(formatted);
		assertThat(uc).isEqualTo(fc).isEqualTo(31);
	}

	@Test
	public void shouldCountJavaFilesOnly() throws Exception {
		Path p = Paths.get("src/test/resources/container/dir");
		lc = new LineCounter(Configuration.ofCmdLine("-d", p.toString()), new ConsolePrinter());
		assertThat(lc.count()).isZero();
	}

	@Test
	public void shouldCount() throws Exception {
		Path p = Paths.get("src/test/resources/container");
		lc = new LineCounter(Configuration.ofCmdLine("-d", p.toString()), new ConsolePrinter());
		assertThat(lc.count()).isEqualTo(31);
	}

	@Test
	public void shouldExcludeFoldersSpecifiedInConfiguration() throws Exception {
		Path p = Paths.get("src/test/resources");
		lc = new LineCounter(
			Configuration.ofCmdLine("-d", p.toString(), "-e", "container"),
			new ConsolePrinter());
		assertThat(lc.count()).isEqualTo(62);
	}
}
