package de.baumato.loc;

import static de.baumato.loc.Messages.DIR_DOES_NOT_EXIST;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.ParserProperties;

/**
 * Holds the command line arguments
 *
 * @author baumato
 *
 */
public class Configuration {

	@Option(
			name = "-d",
			required = true,
			usage = "ARGUMENT_DIRECTORY_USAGE",
			metaVar = "PATH",
			hidden = false,
			aliases = { "--directory" })
	private Path directory;

	static Optional<Configuration> ofCmdLine(String... args) {
		Configuration conf = new Configuration();
		CmdLineParser parser = new CmdLineParser(conf, ParserProperties.defaults().withUsageWidth(80));
		try {
			parser.parseArgument(args);
			if (!Files.isDirectory(conf.directory)) {
				throw new CmdLineException(parser, DIR_DOES_NOT_EXIST, conf.directory.toString());
			}
			return Optional.of(conf);
		} catch (CmdLineException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println();
			StringWriter w = new StringWriter();
			parser.printUsage(w, Messages.getResourceBundle(), OptionHandlerFilter.ALL);
			System.err.println(w.toString());
			System.err.println();
			System.err.println("Example:");
			System.err.println(parser.printExample(OptionHandlerFilter.ALL, Messages.getResourceBundle()));
			return Optional.empty();
		}
	}

	Path getDirectory() {
		return directory;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getClass().getSimpleName())
			.append(" [directory=")
			.append(directory)
			.append("]")
			.toString();
	}

}
