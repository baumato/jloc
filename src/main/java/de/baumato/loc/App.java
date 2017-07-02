package de.baumato.loc;

import static de.baumato.loc.Throwables.getStackTraceAsString;

import de.baumato.loc.Configuration.InvalidCommandLineArgumentsException;
import de.baumato.loc.printer.ConsolePrinter;

/**
 * Counts the lines of code of all java files within one directory recursively.
 *
 * @author baumato
 *
 */
public class App {

	public static void main(String[] args) {
		ConsolePrinter printer = new ConsolePrinter();
		try {
			printer.startProgress();
			long numberOfLines = new LineCounter(Configuration.ofCmdLine(args)).count();
			printer.done(numberOfLines);
		} catch (InvalidCommandLineArgumentsException e) {
			printer.doneWithError(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			printer.doneWithError(Messages.UNEXPECTED_ERROR.format(getStackTraceAsString(e)));
		}
	}
}
