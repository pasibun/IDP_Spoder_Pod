package org.nhl.spoderpod.hexapod;

import org.nhl.spoderpod.hexapod.components.C_Logger;
import org.nhl.spoderpod.hexapod.components.C_RandomTalker;
import org.nhl.spoderpod.hexapod.components.C_ServerListener;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		//WebAppClient webAppC = new WebAppClient(8080);
		//webAppC.start();
		System.out.println("Started service");
		Service s = new Service("RandomService", new IComponent[] { new C_Logger("Logger"),
																	new C_RandomTalker("Talker"),
																	new C_ServerListener("Server")});

		s.start();
		Thread.sleep(10 * 1000);
		s.stop();
		
	}
}

/*

*/