package org.nhl.spoderpod.hexapod.vision;

import org.nhl.spoderpod.hexapod.components.C_Logger;
import org.nhl.spoderpod.hexapod.components.C_RandomTalker;
import org.nhl.spoderpod.hexapod.components.C_ServerListener;
import org.nhl.spoderpod.hexapod.components.C_VisionListener;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Started service");

		Service s = new Service("Vision", new I_Component[] { new C_VisionListener("Vision")});
		s.start();
		Thread.sleep(10 * 1000);
		s.stop();
		
	}
}

/*

*/