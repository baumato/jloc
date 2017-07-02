package de.baumato.loc;

import static de.baumato.loc.Throwables.getStackTraceAsString;

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

}
