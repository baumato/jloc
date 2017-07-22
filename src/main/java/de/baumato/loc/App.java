package de.baumato.loc;

import static de.baumato.loc.util.Throwables.getStackTraceAsString;

import de.baumato.loc.configuration.Configuration;
import de.baumato.loc.configuration.InvalidCommandLineArgumentsException;
import de.baumato.loc.messages.Messages;
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
			Configuration conf = Configuration.ofCmdLine(args);
			printer.setIsVerbose(conf.isVerbose());
			printer.startProgress();
			long numberOfLines = new LineCounter(conf, printer).count();
			if (!conf.isVerbose()) {
				printer.done(numberOfLines);
			}
		} catch (InvalidCommandLineArgumentsException e) {
			printer.doneWithError(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			printer.doneWithError(Messages.UNEXPECTED_ERROR.format(getStackTraceAsString(e)));
		}
	}
}
