package org.nhl.spoder.hexapod.httpservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.nhl.spoderpod.hexapod.core.Message;
import org.nhl.spoderpod.hexapod.interfaces.IThreaded;

/***
 * Handles RouterService communication. 
 * Lang from done because routerService is not yet in production. 
 * @author Driving Ghost
 *
 */
public class CRouterSocket implements IThreaded{
	
	private final String strIPAddress;
	private final int intPort;
	private Socket routerSocket;
	private boolean connected = false; 
	private volatile boolean running;
	private ObjectOutputStream oos;
	private final Thread thread;
	
	public CRouterSocket(){
		this.strIPAddress = "127.0.0.1";
		this.thread = new Thread(this);
		this.intPort = 1234;	
	}
	
	//send a message to the routerService (CAN BE REMOVED ACCORDING TO ARCHITECTURE PLAN?)
	public void write(Message objMessage){
		try{
			ObjectOutputStream  oos = new ObjectOutputStream(routerSocket.getOutputStream());
			oos.writeObject(objMessage);
			oos.close();
		}
		catch(Exception e){
		}
	}
	
	//unfinished method that is to read incomming messages. 
	public void read(){
		
	}
	
	//Sets up the connection to the Socket. 
	//Does not yet call any methods that listen to incomming data. 
	public void run() {
		//init();
		while(!connected){
			try {
				routerSocket = new Socket(strIPAddress, intPort);
				if(routerSocket.isConnected()){
					System.out.println("I am connected");
					this.connected = true;
				}
			} catch (Exception e) {
				//System.out.println("Nope");
			} 
		}
		System.out.println("System up and running.");
		while(true){
			
		}
	}
	//Method to initialize objects. 
	private void init(){
		while(!connected){
			try {
				routerSocket = new Socket(strIPAddress, intPort);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		System.out.println("System up and running.");
	}
	
	//unfinished listen method. Can be read instead? 
	private void listen(){
		
	}
	
	//starts the thread. 
	public void start() {
		if (!this.running) {
			this.running = true;
			this.thread.start();
		}
	}

	//stop the thread. 
	public void stop() {
		try {
			
			this.running = false;
			this.thread.interrupt();
			routerSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
