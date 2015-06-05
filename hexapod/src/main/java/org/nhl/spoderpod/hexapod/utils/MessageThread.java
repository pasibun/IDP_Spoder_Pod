package org.nhl.spoderpod.hexapod.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;

public class MessageThread implements I_Threaded {
	private final Thread thread;
	private final Socket socket;
	private final ObjectOutputStream out;
	private final RouterServer server;
	private volatile boolean running;

	public MessageThread(RouterServer server, Socket socket) {
		this.thread = new Thread(this);
		this.server = server;
		this.socket = socket;
		this.out = Utils.CreateObjectOutputStream(socket);
		this.running = false;
	}

	public void start() {
		if (!this.running) {
			this.thread.start();
		}
	}

	public void stop() {
		this.running = false;
		this.thread.interrupt();
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		this.out.close();
		this.socket.close();
	}

	public void run() {
		if (this.running || Thread.currentThread() != this.thread) {
			return;
		}
		ObjectInputStream in = Utils.CreateObjectinputStream(socket);
		this.running = true;
		while (this.running) {

			try {
				server.sendObject(this, in.readObject());
			} catch (Exception e) {
			}
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendObject(Object object) {
		try {
			this.out.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
