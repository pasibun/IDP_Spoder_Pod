package org.nhl.spoder.hexapod.sensorservice;

import org.nhl.spoderpod.hexapod.components.C_RouterClient;
import org.nhl.spoderpod.hexapod.components.C_SensorFormatter;
import org.nhl.spoderpod.hexapod.components.C_SensorReader;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

public class Main {
	/*
	 * Starts the service.
	 */
	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("SensorService", new I_Component[] {
				new C_SensorReader("Reader"),
				new C_SensorFormatter("DataFormat"),
				new C_RouterClient("RouterClient", "127.0.0.1", 1234) });
		s.start();
	}
}
