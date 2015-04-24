package org.nhl.spoderpod.hexapod.utils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.interfaces.IThreaded;

public final class InputServer implements IThreaded {
	private final Thread thread;
	private final ServerSocket server;
	private final Queue<Socket> connectedClients;
	private volatile boolean running;

	public InputServer(int port) {
		this.thread = new Thread(this);
		this.server = CreateServerSocket(port);
		this.connectedClients = new ConcurrentLinkedQueue<Socket>();
		this.running = false;
	}

	private ServerSocket CreateServerSocket(int port) {
		try {
			return new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean hasConnectedClients() {
		return this.connectedClients.size() > 0;
	}

	public void send(String msg) {
		String data = String.format("HTTP/1.1 200 OK\r\nContent-Length: %d\r\n\r\n%s\r\n", msg.length() + 2, msg);
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
