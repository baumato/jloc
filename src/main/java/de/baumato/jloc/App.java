package de.baumato.jloc;

import static de.baumato.jloc.util.Throwables.getStackTraceAsString;

import de.baumato.jloc.configuration.Configuration;
import de.baumato.jloc.configuration.InvalidCommandLineArgumentsException;
import de.baumato.jloc.messages.Messages;
import de.baumato.jloc.printer.ConsolePrinter;

/**
 * Counts the lines of code of all java files within one directory recursively.
 *
 * @author baumato
 *
 */
public class App {

	public static void main(String... args) {
		var printer = new ConsolePrinter();
		try {
			var conf = Configuration.ofCmdLine(args);
			conf.getAppLogger().debug("configuration={}", conf);
			printer.setIsVerbose(conf.isVerbose());
			printer.startProgress();

			long numberOfLines = new LineCounter(conf, printer).count();
			if (!conf.isVerbose()) {
				printer.done(String.valueOf(numberOfLines));
			}
		} catch (InvalidCommandLineArgumentsException e) {
			printer.doneWithError(e.getLocalizedMessage());
		} catch (Throwable e) {
			printer.doneWithError(Messages.UNEXPECTED_ERROR.format(getStackTraceAsString(e)));
		}
	}
}
