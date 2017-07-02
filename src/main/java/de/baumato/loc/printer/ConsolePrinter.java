package de.baumato.loc.printer;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 *
 * @author baumato
 *
 */
public class ConsolePrinter {

	private final Animation animation;

	public ConsolePrinter() {
		animation = new Animation();
	}

	public void startProgress() {
		animation.startAnimation();
	}

	public void done(Object msg) {
		stopProgress();
		System.out.println(String.valueOf(msg));
	}

	public void doneWithError(Object msg) {
		stopProgress();
		System.err.println(String.valueOf(msg));
	}

	private void stopProgress() {
		animation.stopAnimation();
		try {
			System.out.write("\r".getBytes());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
