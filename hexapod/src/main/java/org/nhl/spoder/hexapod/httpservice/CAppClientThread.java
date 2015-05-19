package org.nhl.spoder.hexapod.httpservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.nhl.spoderpod.hexapod.interfaces.IThreaded;

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
	
	public void run() {
		System.out.println("CAppClient running");
		while(true){
			
			if(CBuffer.getstrListOutSize() != 0){
				System.out.println("Wow!");
				send(CBuffer.getListOutData());
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
	
	public void send(String msg) {
		try {
			String data = String.format("HTTP/1.1 200 OK\r\n"
					+ "Content-Type: text/json\r\n"
					+ "Access-Control-Allow-Origin: *\r\n"
					+ "Content-Length: %d\r\n\r\n" + "%s\r\n",
					msg.length() + 2, msg);

			out.println(data);	
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
