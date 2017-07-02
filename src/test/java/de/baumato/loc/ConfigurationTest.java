package de.baumato.loc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ConfigurationTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void shouldNotCreateConfigurationWhenNoArgsAreGiven() throws Exception {
		assertThat(Configuration.ofCmdLine(new String[0])).isNotPresent();
	}

	@Test
	public void shouldNotCreateConfigurationWhenFileIsGiven() throws Exception {
		File file = tempFolder.newFile();
		assertThat(Configuration.ofCmdLine("-d", file.toString())).isNotPresent();
	}

	@Test
	public void shouldNotCreateConfigurationWhenGivenDirDoesNotExist() throws Exception {
		assertThat(Configuration.ofCmdLine("-d", "/tmp/" + UUID.randomUUID())).isNotPresent();
	}

	@Test
	public void shouldCreateConfiguarationWhenAllMandatoryArgsAreGiven() throws Exception {
		File file = tempFolder.getRoot();
		Configuration conf = Configuration.ofCmdLine("-d", file.getPath()).get();
		assertThat(conf.getDirectory()).isEqualTo(file.toPath());
	}

	@Test
	public void shouldImplementToString() throws Exception {
		Configuration conf = Configuration.ofCmdLine("-d", tempFolder.getRoot().getPath()).get();
		assertThat(conf.toString()).contains(tempFolder.getRoot().getPath());
	}

}
