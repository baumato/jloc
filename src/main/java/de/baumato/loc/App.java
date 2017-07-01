package de.baumato.loc;

/**
 * Counts the lines of code of all java files within one directory recursively.
 *
 * @author baumato
 *
 */
public class App {

	public static void main(String[] args) {
		Configuration conf = Configuration.ofCmdLine(args);
		System.out.println(conf);
	}
}
