package org.nhl.spoder.hexapod.httpservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.core.MessageBus;
import org.nhl.spoderpod.hexapod.interfaces.IMessage;
import org.nhl.spoderpod.hexapod.interfaces.IThreaded;
import org.nhl.spoderpod.hexapod.utils.InputServer;

/***
 * CAppSocket's purpose in life is to redirect the data, formatted by the
 * CFormat class to every client connected to the server. The clients are in a
 * static list in CAppListner CAppListner also takes care of incomming clients.
 * 
 * @author Driving Ghost
 *
 */
public class CAppSocket implements IThreaded {

	private final Thread thread;
	private ServerSocket socketServer;
	private Socket clientSocket;
	private final int intPort;
	private ArrayList<CAppClientThread> connectedClients;
	private volatile boolean running;
	private PrintWriter out;

	public CAppSocket(int port) throws IOException {
		this.thread = new Thread(this);
		this.intPort = port;
		this.connectedClients = new ArrayList<CAppClientThread>();
		this.socketServer = new ServerSocket(intPort);
	}

	public void stop() {
		this.running = false;
		for (CAppClientThread client : connectedClients) {
			try {
				client.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			socketServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				clientSocket = socketServer.accept();
				System.out.println("New Connection Established");
				(new Thread(new CAppClientThread(clientSocket))).start();
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Connection Error");
			}
		}
	}

	public void start() {
		if (!this.running) {
			this.running = true;
			this.thread.start();
		}
	}
	
	public void send(){
		
	}

}
