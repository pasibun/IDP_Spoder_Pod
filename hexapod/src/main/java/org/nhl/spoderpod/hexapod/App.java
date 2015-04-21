package org.nhl.spoderpod.hexapod;

import org.nhl.spoderpod.hexapod.components.CLogger;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;


/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException {
		Service s = new Service(new IComponent[] {
				new CLogger(1) });
		s.start();
		Thread.sleep(10 * 1000);
		s.stop();
	}
}
