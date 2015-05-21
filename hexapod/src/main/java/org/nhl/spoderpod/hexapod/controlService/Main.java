package org.nhl.spoderpod.hexapod.controlService;

import org.nhl.spoderpod.hexapod.components.CControlCheck;
import org.nhl.spoderpod.hexapod.components.CLogger;
import org.nhl.spoderpod.hexapod.components.CServerListener;
import org.nhl.spoderpod.hexapod.components.ControlSocket;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Started service");
		//Service s = new Service("ControlService", new IComponent[] { new CLogger("Logger"),	
		//															new CControlCheck("ControlState"),
		//															new CServerListener("Server")});
		ControlSocket test = new ControlSocket("127.0.0.1", 8080);
		//s.start();
		test.Connect();
	}	
}
