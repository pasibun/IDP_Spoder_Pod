package org.nhl.spoderpod.hexapod;

import org.nhl.spoderpod.hexapod.components.CLogger;
import org.nhl.spoderpod.hexapod.components.CRandomTalker;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Started service");
		Service s = new Service("RandomService", new IComponent[] { new CLogger("Logger"),
																	new CRandomTalker("Talker")});
		s.start();
		Thread.sleep(10 * 1000);
		s.stop();
	}
}
