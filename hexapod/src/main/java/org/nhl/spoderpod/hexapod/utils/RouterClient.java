package org.nhl.spoderpod.hexapod.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.interfaces.I_Message;
import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;

/**
 * Client that handles sending and receiving objects from a socket.
 * 
 * @author achmed
 *
 */
public final class RouterClient implements I_Threaded {
	private final Thread thread;
	private final Socket socket;
	private final ObjectOutputStream outputStream;
	private final Queue<I_Message> receivedMessages;

	public RouterClient(String host, int port) {
		this.thread = new Thread(this);
		this.socket = Utils.CreateSocket(host, port);
		this.outputStream = Utils.CreateObjectOutputStream(socket);
		this.receivedMessages = new ConcurrentLinkedQueue<I_Message>();
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
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void send(I_Message message) {
		try {
			this.outputStream.writeObject(message);
			this.outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasMessages() {
		return this.receivedMessages.size() > 0;
	}

	public I_Message pollMessage() {
		return this.receivedMessages.poll();
	}

	public void start() {
		this.thread.start();
	}

	public void stop() {
		this.thread.interrupt();
	}

	public void run() {
		if (this.thread != Thread.currentThread() || this.socket == null
				|| this.outputStream == null) {
			return;
		}
		ObjectInputStream inputStream = Utils.CreateObjectinputStream(socket);
		while (this.socket.isConnected()) {
			try {
				this.receivedMessages.add((I_Message) inputStream.readObject());
			} catch (Exception e) {
			}
		}
	}
}
