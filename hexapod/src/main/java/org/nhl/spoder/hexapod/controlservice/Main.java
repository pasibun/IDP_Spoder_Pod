package org.nhl.spoder.hexapod.controlservice;

import org.nhl.spoderpod.hexapod.components.C_ControlCheck;
import org.nhl.spoderpod.hexapod.components.C_RouterClient;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("ControlService", new I_Component[] {
				new C_ControlCheck("ControlCheck"),
				new C_RouterClient("RouterClient","127.0.0.1",1234)});
		s.start();
	}
}
