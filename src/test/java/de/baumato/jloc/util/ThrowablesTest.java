package de.baumato.jloc.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ThrowablesTest {

	@Test
	public void shouldConvertExceptionToString() throws Exception {
		Exception e = new RuntimeException("message");
		String stackTrace = Throwables.getStackTraceAsString(e);
		assertThat(stackTrace).contains("RuntimeException", "message");
	}
}
