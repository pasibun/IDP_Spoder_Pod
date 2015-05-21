package org.nhl.spoder.hexapod.httpservice;

import java.io.IOException;

import org.nhl.spoderpod.hexapod.WebAppClient;

public class Refactor {

	private CAppSocket socketApp;
	private CFormat formatter;
	private CRouterSocket socketRouter;
	private WebAppClient webApp;
	private final int intPort;
	
	public Refactor(){
		//System.out.println("Refactor made.");
		intPort = 8080;
	}
	
	//initaliseert Format, AppSocket en RouterCommunicator
	public void init(){
	}
	
	//Run start de Threads.
	public void run(){
		try {
			(new Thread(new CAppSocket(intPort))).start();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		System.out.println("I come here"); 
		(new Thread(new CFormat())).start();
	}
	
	
	
}
