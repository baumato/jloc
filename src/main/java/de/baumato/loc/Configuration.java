package de.baumato.loc;

import java.nio.file.Path;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.ParserProperties;

public class Configuration {

	@Option(
			name = "-d",
			required = true,
			usage = "the directory to be analysed",
			hidden = false,
			aliases = { "--directory" })
	private Path directory;

	static Configuration ofCmdLine(String[] args) {
		Configuration conf = new Configuration();
		CmdLineParser parser = new CmdLineParser(conf, ParserProperties.defaults().withUsageWidth(80));
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			System.err.println();
			parser.printUsage(System.err);
			System.err.println();
			System.err.println("Example:");
			System.err.println(parser.printExample(OptionHandlerFilter.ALL, null));
			System.exit(1);
		}
		return conf;
	}

	Path getDirectory() {
		return directory;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Configuration [directory=").append(directory).append("]").toString();
	}

}
