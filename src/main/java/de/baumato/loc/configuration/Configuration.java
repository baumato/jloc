package de.baumato.loc.configuration;

import static de.baumato.loc.messages.Messages.DIR_DOES_NOT_EXIST;

import java.nio.file.Files;
import java.nio.file.Path;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
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

	public static Configuration ofCmdLine(String... args) throws InvalidCommandLineArgumentsException {
		Configuration conf = new Configuration();
		CmdLineParser parser = new CmdLineParser(conf, ParserProperties.defaults().withUsageWidth(80));
		try {
			parser.parseArgument(args);
			if (!Files.isDirectory(conf.directory)) {
				throw new CmdLineException(parser, DIR_DOES_NOT_EXIST, conf.directory.toString());
			}
			return conf;
		} catch (CmdLineException e) {
			throw new InvalidCommandLineArgumentsException(parser, e);
		}
	}

	public Path getDirectory() {
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
