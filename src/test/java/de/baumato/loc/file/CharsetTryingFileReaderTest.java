package de.baumato.loc.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.File;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.base.Charsets;

public class CharsetTryingFileReaderTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void shouldReadUtf8File() throws Exception {
		File file = folder.newFile();
		String expected = "Hello\nWorld!\n0815\nxyz";
		Files.writeString(file.toPath(), expected, Charsets.UTF_8);
		String actual = new CharsetTryingFileReader(file.toPath()).read();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void shouldReadIso88591File() throws Exception {
		File file = folder.newFile();
		String expected = "Hello\nWorld!\näöü\nxyz";
		Files.writeString(file.toPath(), expected, Charsets.ISO_8859_1);

		// ensure that reading with UTF-8 fails
		Throwable t = catchThrowable(() -> Files.readString(file.toPath(), Charsets.UTF_8));
		assertThat(t).isInstanceOf(MalformedInputException.class);

		// ensure that CharsetTryingFileReader usues the correct charset
		String actual = new CharsetTryingFileReader(file.toPath()).read();
		assertThat(actual).isEqualTo(expected);
	}

}
