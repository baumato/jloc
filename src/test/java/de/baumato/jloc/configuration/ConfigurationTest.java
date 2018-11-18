package de.baumato.jloc.configuration;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.impl.SimpleLogger;

public class ConfigurationTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@BeforeClass
	public static void setup() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void shouldNotCreateConfigurationWhenNoArgsAreGiven() throws Exception {
		Throwable t = catchThrowable(() -> Configuration.ofCmdLine(new String[0]));
		assertThat(t).isInstanceOf(InvalidCommandLineArgumentsException.class)
			.hasMessageContaining("is required");
	}

	@Test
	public void shouldNotCreateConfigurationWhenFileIsGiven() throws Exception {
		File file = tempFolder.newFile();
		Throwable t = catchThrowable(() -> Configuration.ofCmdLine("-d", file.toString()));
		assertThat(t).isInstanceOf(InvalidCommandLineArgumentsException.class)
			.hasMessageContaining(file.toString());
	}

	@Test
	public void shouldNotCreateConfigurationWhenGivenDirDoesNotExist() throws Exception {
		String unavailableDir = UUID.randomUUID().toString();
		Throwable t = catchThrowable(() -> Configuration.ofCmdLine("-d", "/tmp/" + unavailableDir));
		assertThat(t).isInstanceOf(InvalidCommandLineArgumentsException.class)
			.hasMessageContaining(unavailableDir);
	}

	@Test
	public void shouldCreateConfiguarationWhenAllMandatoryArgsAreGiven() throws Exception {
		File file = tempFolder.getRoot();
		Configuration conf = Configuration.ofCmdLine("-d", file.getPath());
		assertThat(conf.getDirectory()).isEqualTo(file.toPath());
	}

	@Test
	public void shouldCreateConfigurationWithExcludeDirsArgument() throws Exception {
		File file = tempFolder.getRoot();
		Configuration conf = Configuration.ofCmdLine("-d", file.getPath(), "-e", "target", "bin");
		assertThat(conf.getExcludeDirs()).hasSameElementsAs(Arrays.asList("target", "bin"));
	}

	@Test
	public void shouldInitVerboseFlag() throws Exception {
		Configuration c = Configuration.ofCmdLine("-d", tempFolder.getRoot().getPath(), "-v");
		assertThat(c.isVerbose()).isTrue();
	}

	@Test
	public void shouldInitAppLogger() throws Exception {
		Configuration c = Configuration.ofCmdLine("-d", tempFolder.getRoot().getPath(), "-v");
		assertThat(c.getAppLogger()).isInstanceOf(SimpleLogger.class);

		c = Configuration.ofCmdLine("-d", tempFolder.getRoot().getPath(), "-v");
		assertThat(c.isDebugEnabled()).isFalse();

		c = Configuration.ofCmdLine("-d", tempFolder.getRoot().getPath(), "-X");
		assertThat(c.isDebugEnabled()).isTrue();
	}

	@Test
	public void shouldImplementToString() throws Exception {
		Configuration conf = Configuration.ofCmdLine("-d", tempFolder.getRoot().getPath());
		assertThat(conf.toString()).contains(tempFolder.getRoot().getPath());
	}

}
