package org.nhl.spoder.hexapod.routerservice;

import org.nhl.spoderpod.hexapod.components.C_HTTPAppSocket;
import org.nhl.spoderpod.hexapod.components.C_HTTPFormat;
import org.nhl.spoderpod.hexapod.components.C_RouterServer;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;
import org.nhl.spoderpod.hexapod.utils.U_RouterServer;

public class Main {

	public static void main(String[] args)  throws InterruptedException {
		Service s = new Service("RouterService", new I_Component[] { 	
				new C_RouterServer("RouterServer")});
		
				
		s.start();
		Thread.sleep(10*1000);
		s.run();
	}

}
