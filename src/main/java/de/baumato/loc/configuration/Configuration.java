package de.baumato.loc.configuration;

import static de.baumato.loc.messages.Messages.DIR_DOES_NOT_EXIST;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.slf4j.spi.LocationAwareLogger;

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

	@Option(
			name = "-X",
			required = false,
			usage = "ARGUMENT_DEBUG_OUTPUT",
			metaVar = "BOOLEAN",
			hidden = false,
			aliases = { "--debug" })
	private boolean debug;

	@Option(
			name = "-cm",
			required = false,
			usage = "ARGUMENT_CALCULATION_MODE",
			// metaVar = "STRING",
			hidden = false,
			aliases = { "--calculation-mode" })
	private CalculationMode calculationMode = CalculationMode.LOC;

	public static Configuration ofCmdLine(String... args) throws InvalidCommandLineArgumentsException {
		Configuration conf = new Configuration();
		CmdLineParser parser = new CmdLineParser(conf, ParserProperties.defaults().withUsageWidth(80));
		try {
			parser.parseArgument(args);
			if (!conf.directory.toFile().isDirectory()) {
				throw new CmdLineException(parser, DIR_DOES_NOT_EXIST, conf.directory.toString());
			}
		} catch (CmdLineException e) {
			throw new InvalidCommandLineArgumentsException(parser, e);
		}
		configureAppLogger(conf);
		return conf;
	}

	private static void configureAppLogger(Configuration conf) {
		try {
			SimpleLogger logger = (SimpleLogger) conf.getAppLogger();
			Field f = SimpleLogger.class.getDeclaredField("currentLogLevel");
			f.setAccessible(true);
			int level = conf.debug ? LocationAwareLogger.TRACE_INT : LocationAwareLogger.ERROR_INT;
			f.set(logger, level);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	public Logger getAppLogger() {
		return LoggerFactory.getLogger("App");
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

	boolean isDebugEnabled() {
		return debug;
	}

	public CalculationMode getCalculationMode() {
		return calculationMode;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Configuration [directory=")
			.append(directory)
			.append(", excludeDirs=")
			.append(excludeDirs)
			.append(", verbose=")
			.append(verbose)
			.append(", debug=")
			.append(debug)
			.append(", calculationMode=")
			.append(calculationMode)
			.append("]")
			.toString();
	}
}
