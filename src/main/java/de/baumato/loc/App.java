package de.baumato.loc;

import java.io.PrintWriter;
import java.io.StringWriter;

import de.baumato.loc.Configuration.InvalidCommandLineArgumentsException;

/**
 * Counts the lines of code of all java files within one directory recursively.
 *
 * @author baumato
 *
 */
public class App {

	public static void main(String[] args) {
		try {
			long numberOfLines = new LineCounter(Configuration.ofCmdLine(args)).count();
			System.out.println(numberOfLines);
		} catch (InvalidCommandLineArgumentsException e) {
			System.err.println(e.getMessage());
		} catch (RuntimeException e) {
			System.err.println(Messages.UNEXPECTED_ERROR.format(getStackTraceAsString(e)));
		}
	}

	private static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
