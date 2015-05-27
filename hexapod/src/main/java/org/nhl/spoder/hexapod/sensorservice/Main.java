package org.nhl.spoder.hexapod.sensorservice;

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
																	new C_SensorFormatter("DataFormat") });
		s.start();
		Thread.sleep(10 * 1000);
		s.run();
	}
}
