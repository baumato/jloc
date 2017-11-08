package de.baumato.loc.printer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.stream.Stream;

@SuppressWarnings("squid:S106") // Allow to use System.out since, this is console printer
final class Animator extends Thread {

	private static final String SYMOBLS = "|/⎯\\";

	@Override
	public void run() {
		Iterator<Character> infiniteSymbols = Stream.iterate(0, i -> (i + 1) % SYMOBLS.length())
			.map(SYMOBLS::charAt)
			.iterator();
		while (!isInterrupted()) {
			try {
				System.out.write(toProgress(infiniteSymbols));
				Thread.sleep(100);
			} catch (IOException | InterruptedException e) {
				interrupt();
			}
		}
	}

	private static byte[] toProgress(Iterator<Character> infiniteSymbols) {
		char c = infiniteSymbols.next().charValue();
		String progress = c + " \r";
		return progress.getBytes(StandardCharsets.UTF_8);
	}

	public void startAnimation() {
		start();
	}

	public void stopAnimation() {
		if (!isAlive()) {
			return;
		}
		interrupt();
		try {
			this.join();
		} catch (InterruptedException e) {
			interrupt();
		}
		try {
			System.out.write("\r".getBytes());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
