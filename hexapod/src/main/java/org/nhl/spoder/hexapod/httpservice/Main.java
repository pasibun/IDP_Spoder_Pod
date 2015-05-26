package org.nhl.spoder.hexapod.httpservice;

import org.nhl.spoderpod.hexapod.components.C_HTTPAppSocket;
import org.nhl.spoderpod.hexapod.components.C_HTTPFormat;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * @author Driving Ghost
 */
public class Main {
	
	/*
	 * Starts the service.
	 */
	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("HttpService", new IComponent[] { 	new C_HTTPAppSocket("AppSocket", 8080),
																	new C_HTTPFormat("Formatter")});
		s.start();
		Thread.sleep(10*1000);
		s.run();
	}
}