package org.nhl.spoder.hexapod.movementservice;

import org.nhl.spoderpod.hexapod.components.C_Movement;
import org.nhl.spoderpod.hexapod.components.C_RouterClient;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * @author Fre-Meine gayass
 */
public class Main {
	/**
	 * TODO: Welke components moeten hier bij?
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("MovementService", new I_Component[] {
				new C_Movement("C_Movement"),
				new C_RouterClient("C_RouterClient", "127.0.0.1", 1234) });
		s.start();

	}
}