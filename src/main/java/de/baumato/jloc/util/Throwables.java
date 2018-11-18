package de.baumato.jloc.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utility methods dealing with Throwables.
 *
 * @author baumato
 *
 */
public class Throwables {

	private Throwables() {
		// prevent instantiation
	}

	public static String getStackTraceAsString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
}
