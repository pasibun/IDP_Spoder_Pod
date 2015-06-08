package org.nhl.spoderpod.hexapod.utils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;

public class U_HTTPAppServer implements I_Threaded {

	private final Thread thread;
	private final ServerSocket socketServer;
	private final Queue<Socket> queueSocketClient;
	private volatile boolean running;
	
	public U_HTTPAppServer(int intPort){
		this.thread = new Thread(this);
		this.socketServer = Utils.CreateServerSocket(intPort);
		this.queueSocketClient = new ConcurrentLinkedQueue<Socket>();
		this.running = false;
	}
	
	/***
	 * Method checks if there are any clients queued up in the 
	 * connectedClients queue. 
	 * @return boolean value based on if the amount of clients is list is bigger than 0. 
	 */
	public boolean hasClients(){
		return this.queueSocketClient.size() > 0;
	}
	
	/***
	 * 
	 * @param strMessage
	 * @return
	 */
	private String addHeader(String strMessage){
		return String.format("HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/json\r\n"
				+ "Access-Control-Allow-Origin: *\r\n"
				+ "Content-Length: %d\r\n\r\n" 
				+ "%s\r\n", strMessage.length() + 2, strMessage);
	}
	
	/***
	 * Method sends formatted data to all the clients in the connectedClients list.  
	 * @param msg string message formatted into JSON. 
	 */
	public void send(String strMessage) {
		while (hasClients()) {
			try {
				System.out.println("Trying to send.");
				Socket client = this.queueSocketClient.poll();
				client.getOutputStream().write(addHeader(strMessage).getBytes());
				client.getOutputStream().flush();
				client.close();
				System.out.println(queueSocketClient.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * Close method goes through the SocketClient list and closes every connected client.
	 * Afterwards, reduces the life of the socketServer to zero. 
	 */
	private void close() {
		while (this.queueSocketClient.size() > 0) { 
			try {
				this.queueSocketClient.poll().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			this.socketServer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * The run method here makes sure the server keeps running and waiting for clients. 
	 */
	public void run() {
		if (this.socketServer == null || this.running
				|| Thread.currentThread() != this.thread) {
			return;
		}
		this.running = true;
		while (this.running) {
			try {
				this.queueSocketClient.add(this.socketServer.accept());
				
			} catch (Exception e) {
			}
		}
	}
	/***
	 * start method starts the thread. 
	 */
	public void start() {
		if (!this.running) {
			this.thread.start();
		}
	}

	/***
	 * Stop method stops the thread and calls the close method(), closing all sockets and the server. 
	 */
	public void stop() {
		this.running = false;
		this.thread.interrupt();
		close();
	}
	
}
