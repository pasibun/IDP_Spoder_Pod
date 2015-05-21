package org.nhl.spoder.hexapod.httpservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.interfaces.IThreaded;

/***
 * This class is the thread that contains a client. 
 * The most important part is the send function that sends messages to the client. 
 * @author Driving Ghost
 *
 */
public class CAppClientThread implements IThreaded {

	private Socket socketClient = null;
	private PrintWriter out = null;
	private volatile boolean running;
	private final Thread thread;
	
	public CAppClientThread(Socket s) throws IOException{
		this.thread = new Thread(this);
		this.socketClient = s;
		out = new PrintWriter(socketClient.getOutputStream(), true);
	}
	
	/***
	 * Run starts waits for the buffer to have strings in it, to be send off. 
	 * Buffer class might have to be changed from a list to a string. Requires more synergy!
	 * I'd say yes. 
	 */
	public void run() {
		System.out.println("CAppClient running");
		while(true){
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				
			}
			if(CBuffer.getstrListOutSize() != 0){
				System.out.println("Wow!");
				send(CBuffer.getListOutData());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.nhl.spoderpod.hexapod.interfaces.IThreaded#start()
	 */
	public void start() {
		if (!this.running) {
			this.running = true;
			this.thread.start();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.nhl.spoderpod.hexapod.interfaces.IThreaded#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * send pushes the data through the stream and up the ass of the webapp that is connected. 
	 */
	public void send(String msg) {
		try {
			//DO NOT MODIFY HTTP HEADER WITHOUT LUKAS'S PERMISSION!
			String data = String.format("HTTP/1.1 200 OK\r\n"
					+ "Content-Type: text/json\r\n"
					+ "Access-Control-Allow-Origin: *\r\n"
					+ "Content-Length: %d\r\n\r\n" + "%s\r\n",
					msg.length() + 2, msg);
			out.println(data);	
			out.flush();
			System.out.println("CLIENT: Put on the train already halway.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
