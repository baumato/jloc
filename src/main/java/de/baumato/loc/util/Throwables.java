package de.baumato.loc.util;

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

	public static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
