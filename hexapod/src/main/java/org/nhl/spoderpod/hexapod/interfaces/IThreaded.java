package org.nhl.spoderpod.hexapod.interfaces;

/**
 * Interface classes that could be threaded
 * @author achmed
 */
public interface IThreaded extends Runnable {
	/**
	 * Start the thread
	 */
	public void start();
	
	/**
	 * Stop the thread
	 */
	public void stop();
}
