package de.baumato.jloc;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;

import de.baumato.jloc.SystemStreamRedirectionRule.SystemStream;

public class AppTest {

	@Rule
	public SystemStreamRedirectionRule sysout = new SystemStreamRedirectionRule(SystemStream.OUT);

	@Rule
	public SystemStreamRedirectionRule syserr = new SystemStreamRedirectionRule(SystemStream.ERR);

	@Test
	public void shouldRun() throws Exception {
		App.main("-d", Paths.get("src/test/resources/container").toString());
		assertThat(sysout.getLastLine()).isEqualTo("29");
	}

	@Test
	public void shouldRunInVerboseMode() throws Exception {
		Locale.setDefault(Locale.ENGLISH);
		App.main("-v", "-d", Paths.get("src/test/resources/container").toString());
		assertThat(sysout.getLastLine()).isEqualTo("Sum;29");
	}

	@Test
	public void showErrorWhenInvalidCommandLineGiven() throws Exception {
		App.main("--unknown");
		assertThat(syserr.get()).contains("--unknown");
	}
}
