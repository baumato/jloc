package de.baumato.jloc.configuration;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;

import de.baumato.jloc.messages.Messages;

/**
 * Exception indicates invalid command line arguments.
 *
 * @author baumato
 *
 */
public final class InvalidCommandLineArgumentsException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCommandLineArgumentsException(CmdLineParser parser, CmdLineException e) {
		super(createMessage(parser, e));
	}

	private static String createMessage(CmdLineParser parser, CmdLineException e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println(e.getLocalizedMessage());
		pw.println();
		pw.println(Messages.USAGE.get());
		parser.printUsage(pw, Messages.getResourceBundle(), OptionHandlerFilter.ALL);
		pw.println();
		pw.println(Messages.EXAMPLE.get());
		pw.println(parser.printExample(OptionHandlerFilter.ALL, Messages.getResourceBundle()));
		return sw.toString();
	}

}
