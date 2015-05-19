package org.nhl.spoder.hexapod.httpservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.nhl.spoderpod.hexapod.interfaces.IThreaded;

public class CFormat implements IThreaded {

	private volatile boolean running;
	private final Thread thread;

	public CFormat() {
		this.thread = new Thread(this);
	}

	public void format(String data) {

	}

	public void run() {
		while (true) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				System.out.println("Enter String");
				String x;

				x = br.readLine();
				CBuffer.queueStrListOutData(x);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (!this.running) {
			this.running = true;
			this.thread.start();
		}
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

}
