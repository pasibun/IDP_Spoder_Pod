package org.nhl.spoder.hexapod.sensorservice;

import org.nhl.spoderpod.hexapod.components.C_HTTPAppSocket;
import org.nhl.spoderpod.hexapod.components.C_HTTPFormat;
import org.nhl.spoderpod.hexapod.components.C_SensorReader;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

public class Main {
	/*
	 * Starts the service.
	 */
	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("SensorService", new IComponent[] {
																	new C_SensorReader("Reader", 8080),
																	new C_SensorFormatter("DataFormat") });
		s.start();
		Thread.sleep(10 * 1000);
		s.run();
	}
}
