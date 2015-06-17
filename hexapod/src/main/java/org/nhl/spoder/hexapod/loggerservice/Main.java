package org.nhl.spoder.hexapod.loggerservice;

import org.nhl.spoderpod.hexapod.components.C_Logger;
import org.nhl.spoderpod.hexapod.components.C_RouterClient;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("LoggerService", new I_Component[] {
				new C_Logger("C_Logger"),
				new C_RouterClient("C_RouterClient", "127.0.0.1", 1234)
		});
		s.start();
	}
}
