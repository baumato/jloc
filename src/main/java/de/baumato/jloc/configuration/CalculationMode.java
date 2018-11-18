package de.baumato.jloc.configuration;

public enum CalculationMode {
	LOC(true, true), // physical lines of code including empty lines and comments
	SLOC(false, false); // lines of code without empty lines and comments

	public final boolean countEmptyLines;
	public final boolean countComments;

	private CalculationMode(boolean countEmptyLines, boolean countComments) {
		this.countEmptyLines = countEmptyLines;
		this.countComments = countComments;
	}

}
