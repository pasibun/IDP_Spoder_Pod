package org.nhl.spoderpod.hexapod.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nhl.spoderpod.hexapod.core.ComponentRef;
import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;

/**
 * Server for routing objects.
 * 
 * @author achmed
 *
 */
public final class RouterServer implements I_Threaded {
	private final Thread thread;
	private final ServerSocket serverSocket;
	private final List<MessageThread> connectedClients;

	public RouterServer(int port) {
		this.thread = new Thread(this);
		this.serverSocket = Utils.CreateServerSocket(port);
		this.connectedClients = Collections
				.synchronizedList(new ArrayList<MessageThread>());

	}

	/**
	 * Close the connections.
	 * 
	 * @param socket
	 * @return
	 */
	private boolean close(Socket socket) {
		try {
			socket.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Add the client to the connected client list. If a service name already
	 * exists it closes that connection.
	 * 
	 * @param client
	 */
	private void handleNewConnection(Socket client) {
		MessageThread m = new MessageThread(this, client);
		this.connectedClients.add(m);
		m.start();
	}

	public void start() {
		this.thread.start();
	}

	public void stop() {
		this.thread.interrupt();
	}

	public void sendObject(MessageThread sender, Object object) {
		for (MessageThread m : connectedClients) {
			if (m != sender) {
				try {
					m.sendObject(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void run() {
		if (this.serverSocket != null && this.thread != Thread.currentThread()) {
			return;
		}
		while (!this.thread.isInterrupted()) {
			try {
				handleNewConnection(this.serverSocket.accept());
				System.out.println("connected!");
			} catch (Exception e) {
			}
		}
	}
}
