package de.baumato.loc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class LineCounterTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testCount() throws Exception {
		LineCounter lc = new LineCounter(Configuration.ofCmdLine("-d", folder.getRoot().toString()));
	}

}
