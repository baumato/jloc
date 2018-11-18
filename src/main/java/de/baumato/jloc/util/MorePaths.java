package de.baumato.jloc.util;

import java.nio.file.Path;

public class MorePaths {

	private MorePaths() {
		// prevent instantiation
	}

	public static boolean endsWithIgnoreCase(Path p, String suffix) {
		return p.getFileName().toString().toLowerCase().endsWith(suffix.toLowerCase());
	}
}
