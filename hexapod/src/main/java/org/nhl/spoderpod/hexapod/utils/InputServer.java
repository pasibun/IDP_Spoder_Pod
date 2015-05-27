package org.nhl.spoderpod.hexapod.utils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;

/**
 * Simple httpserver which sends the same data to all connected clients.
 * @author achmed
 *
 */
public final class InputServer implements I_Threaded {
	private final Thread thread;
	private final ServerSocket server;
	private final Queue<Socket> connectedClients;
	private volatile boolean running;

	/**
	 * @param port Port to listen on.
	 */
	public InputServer(int port) {
		this.thread = new Thread(this);
		this.server = Utils.CreateServerSocket(port);
		this.connectedClients = new ConcurrentLinkedQueue<Socket>();
		this.running = false;
	}

	/**
	 * Check if there are connected clients.
	 * @return
	 */
	public boolean hasConnectedClients() {
		return this.connectedClients.size() > 0;
	}

	/**
	 * Send data to all connected clients. the data get's appended with an http-header.
	 * @param msg
	 */
	public void send(String msg) {
		String data = String.format("HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/json\r\n"
				+ "Access-Control-Allow-Origin: *\r\n"
				+ "Content-Length: %d\r\n\r\n" 
				+ "%s\r\n", msg.length() + 2, msg);
		while (hasConnectedClients()) {
			try {
				Socket client = this.connectedClients.poll();
				client.getOutputStream().write(data.getBytes());
				client.close();
			} catch (Exception e) {
			}
		}
	}

	public void start() {
		if (!this.running) {
			this.thread.start();
		}
	}

	public void stop() {
		this.running = false;
		this.thread.interrupt();
		close();
	}

	/**
	 * Closes connection with all connected clients and then closes the server.
	 */
	private void close() {
		while (this.connectedClients.size() > 0) {
			try {
				this.connectedClients.poll().close();
			} catch (Exception e) {
			}
		}
		try {
			this.server.close();
		} catch (Exception e) {
		}
	}

	public void run() {
		if (this.server == null || this.running
				|| Thread.currentThread() != this.thread) {
			return;
		}
		this.running = true;
		while (this.running) {
			try {
				this.connectedClients.add(this.server.accept());
			} catch (Exception e) {
			}
		}
	}
}
