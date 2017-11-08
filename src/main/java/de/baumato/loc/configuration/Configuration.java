package de.baumato.loc.configuration;

import static de.baumato.loc.messages.Messages.DIR_DOES_NOT_EXIST;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

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

	@Option(
			name = "-e",
			required = false,
			usage = "ARGUMENT_EXCLUDE_DIRS_USAGE",
			metaVar = "FOLDER_NAMES",
			hidden = false,
			aliases = { "--excludeDirs" },
			handler = StringArrayOptionHandler.class)
	private List<String> excludeDirs = new ArrayList<>();

	@Option(
			name = "-v",
			required = false,
			usage = "ARGUMENT_VERBOSE_USAGE",
			metaVar = "BOOLEAN",
			hidden = false,
			aliases = { "--verbose" })
	private boolean verbose;

	public static Configuration ofCmdLine(String... args) throws InvalidCommandLineArgumentsException {
		Configuration conf = new Configuration();
		CmdLineParser parser = new CmdLineParser(conf, ParserProperties.defaults().withUsageWidth(80));
		try {
			parser.parseArgument(args);
			if (!conf.directory.toFile().exists()) {
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

	public Collection<String> getExcludeDirs() {
		return excludeDirs;
	}

	public boolean isVerbose() {
		return verbose;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getClass().getSimpleName())
			.append(" [directory=")
			.append(directory)
			.append(", excludeDirs=")
			.append(excludeDirs)
			.append(", verbose=")
			.append(verbose)
			.append("]")
			.toString();
	}
}
