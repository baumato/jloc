package de.baumato.loc;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author baumato
 *
 */
public class Throwables {

	private Throwables() {
		// prevent instantiation
	}

	static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
