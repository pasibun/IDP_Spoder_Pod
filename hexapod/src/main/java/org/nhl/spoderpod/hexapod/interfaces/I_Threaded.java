package org.nhl.spoderpod.hexapod.interfaces;

/**
 * Interface classes that could be threaded
 * @author achmed
 */
public interface I_Threaded extends Runnable {
	/**
	 * Start the thread
	 */
	public void start();
	
	/**
	 * Stop the thread
	 */
	public void stop();
}
