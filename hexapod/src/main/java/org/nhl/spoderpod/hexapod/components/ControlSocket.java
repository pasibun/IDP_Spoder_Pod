package org.nhl.spoderpod.hexapod.components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ControlSocket {
	
	private String ipAdress;
	private int port;
	private boolean isConnected = false;
	
	public ControlSocket(String ipAdress, int port){
		
		this.ipAdress = "127.0.0.1";
		this.port = 8080;
	}
	
	public void Connect(){
		
		while(!isConnected)
		{
			try{
				System.out.println("Connecting to " + ipAdress
	                    + " on port " + port);
				Socket client = new Socket(ipAdress, port);
				if(client.isConnected()){
					System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());		
					isConnected = true;
				}
			}
			catch(IOException e){
				
			}
		}
	}
	
	

}
