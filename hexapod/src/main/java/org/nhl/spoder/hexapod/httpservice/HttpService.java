package org.nhl.spoder.hexapod.httpservice;

import java.io.IOException;

import org.nhl.spoderpod.hexapod.WebAppClient;

/***
 * Hi and welcome to viewing the httpService class! 
 * This service will provide every connected Client with data!
 * This data is made from incomming messages from the RouterService, with the data origin being Logger Data. 
 * 
 * The Threads started are:
 * CFormat - makes sure the message data is put into a JSON friendly format. (NOT FINISHED, LOGGER DATA FORMAT REQUIRED)
 * CAppSocket - Sets up the Server on the spider, that every webapp can connect to. Every connected Client becomes a thread.
 * CRouterSocket - Sets up a connection to the RouterService (NOT FINISHED, ROUTERSERVICE REQUIRED FOR 100% CONFIDENCE)
 * 
 * Sofar only CAppSocket works. 
 * 
 * @version Alpha
 * TODO intergration, intergration, intergration. 
 * @author Driving Ghost
 *
 */
public class HttpService {

	private CAppSocket socketApp;
	private CFormat formatter;
	private CRouterSocket socketRouter;
	private WebAppClient webApp;
	private final int intPort;
	
	public HttpService(){
		//System.out.println("Refactor made.");
		intPort = 8080;
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
		(new Thread(new CRouterSocket())).start();
	}
	
	
	
}
