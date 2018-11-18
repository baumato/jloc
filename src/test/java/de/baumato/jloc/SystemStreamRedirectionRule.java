package de.baumato.jloc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

import org.junit.rules.ExternalResource;

public class SystemStreamRedirectionRule extends ExternalResource {

	public enum SystemStream {
		OUT(System.out, ps -> System.setOut(ps)), //
		ERR(System.err, ps -> System.setErr(ps));

		private final PrintStream value;
		private final Consumer<PrintStream> applyStreamOp;

		private SystemStream(PrintStream stream, Consumer<PrintStream> applyStreamOp) {
			this.value = stream;
			this.applyStreamOp = applyStreamOp;
		}

		private void set(PrintStream ps) {
			applyStreamOp.accept(ps);
		}

	}

	private final SystemStream systemStream;
	private PrintStream old = null;
	private PrintStream current = null;
	private ByteArrayOutputStream bytes = null;

	public SystemStreamRedirectionRule(SystemStream stream) {
		this.systemStream = stream;
	}

	@Override
	protected void before() throws Throwable {
		super.before();
		old = systemStream.value;
		bytes = new ByteArrayOutputStream();
		current = new PrintStream(bytes);
		systemStream.set(current);
	}

	@Override
	protected void after() {
		super.after();
		try {
			if (current != null) {
				current.close();
			}
			current = null;
			bytes = null;
		} finally {
			systemStream.set(old);
		}
	}

	public String get() {
		return new String(bytes.toByteArray());
	}

	public String getLastLine() {
		return lastLine(get());
	}

	private static String lastLine(String s) {
		String[] p = s.trim().split("[\r\n]");
		return p[p.length - 1];
	}
}
