package org.nhl.spoderpod.hexapod.utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.interfaces.IThreaded;


/**
 * Client that handles sending and receiving objects from a socket.
 * @author achmed
 *
 */
public final class RouterClient implements IThreaded {
	private final Thread thread;
	private final Socket socket;
	private final ObjectOutputStream outputStream;

	public RouterClient(String host, int port) {
		this.thread = new Thread(this);
		this.socket = Utils.CreateSocket(host, port);
		this.outputStream = Utils.CreateObjectOutputStream(this.socket);
	}

	/**
	 * Closes connections.
	 */
	private void close() {
		try {
			this.outputStream.close();
			this.socket.close();
		} catch (Exception e) {
		} finally {
		}
	}

	/**
	 * Send a message across the router to another service.
	 * @param message
	 * @throws IOException
	 */
	public void send(IMessage message) throws IOException {
		this.outputStream.writeObject(message);
		this.outputStream.flush();
	}

	public void start() {
		this.thread.start();
	}

	public void stop() {
		this.thread.interrupt();
	}

	public void run() {
		if (this.thread != Thread.currentThread()) {
			return;
		}
		while (this.socket.isConnected()) {
			try {
				thread.sleep(1000);
			} catch (Exception e) {
			}
		}
	}
}
