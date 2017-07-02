package de.baumato.loc.printer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.stream.Stream;

final class Animation extends Thread {

	private static final String SYMOBLS = "|/⎯\\";

	@Override
	public void run() {
		Iterator<Character> infiniteSymbols = Stream.iterate(0, i -> (i + 1) % SYMOBLS.length())
			.map(i -> SYMOBLS.charAt(i))
			.iterator();
		while (!isInterrupted()) {
			try {
				System.out.write(toProgress(infiniteSymbols));
				Thread.sleep(100);
			} catch (IOException e) {
				interrupt();
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}

	private static byte[] toProgress(Iterator<Character> infiniteSymbols) {
		char c = infiniteSymbols.next().charValue();
		String progress = "\r " + c + " ";
		return progress.getBytes(StandardCharsets.UTF_8);
	}

	public void startAnimation() {
		start();
	}

	public void stopAnimation() {
		interrupt();
		try {
			this.join();
		} catch (InterruptedException e) {
			interrupt();
		}
	}
}
