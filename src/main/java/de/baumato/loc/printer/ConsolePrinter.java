package de.baumato.loc.printer;

/**
 *
 * @author baumato
 *
 */
@SuppressWarnings("squid:S106") // Allow to use System.out since, this is console printer
public class ConsolePrinter {

	private final Animator animation;
	private volatile boolean verbose;

	public ConsolePrinter() {
		this.animation = new Animator();
		this.verbose = false;
	}

	public void setIsVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public void startProgress() {
		if (!verbose) {
			animation.startAnimation();
		}
	}

	public void done(Object msg) {
		animation.stopAnimation();
		System.out.println(String.valueOf(msg));
	}

	public void doneWithError(Object msg) {
		animation.stopAnimation();
		System.err.println(String.valueOf(msg));
	}

	public void step(String msg) {
		if (verbose) {
			System.out.println(msg);
		}
	}
}
