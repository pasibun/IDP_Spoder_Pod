package org.nhl.spoderpod.hexapod.interfaces;

public interface IThreaded extends Runnable {
	public void start();
	public void stop();
}
