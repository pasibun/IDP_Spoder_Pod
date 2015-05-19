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
 * Verzorgt de Communicatie met de Router
 * @author Driving Ghost
 *
 */
public class CRouterSocket implements IThreaded{
	
	private final String strIPAddress;
	private final int intPort;
	private Socket routerSocket;
	private boolean connected = false;
	private InputStream streamInStream; 
	private volatile boolean running;
	private ObjectOutputStream oos;
	
	public CRouterSocket(){
		this.strIPAddress = "127.0.0.1";
		this.intPort = 4444;	
	}
	
	public void write(Message objMessage){
		try{
			ObjectOutputStream  oos = new ObjectOutputStream(routerSocket.getOutputStream());
			oos.writeObject(objMessage);
			oos.close();
		}
		catch(Exception e){
		}
		System.out.println("Connection ended");
	}
	
	public void read(){
		
	}

	public void run() {
		while(this.running){
			if(CBuffer.getListOutSize() > 0){
				write(CBuffer.getOutMessage());
			}
		}
	}

	public void start() {

		while(!connected){
			try {
				routerSocket = new Socket(strIPAddress, intPort);
				if(routerSocket.isConnected()){
					connected = true;
				}
			} catch (Exception e) {
				System.out.println("Nope");
			} 
		}
		this.running = true;
		System.out.println("System up and running.");
	}

	public void stop() {
		try {
			routerSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
