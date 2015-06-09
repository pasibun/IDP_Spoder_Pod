package org.nhl.spoder.hexapod.movementservice;

import org.nhl.spoderpod.hexapod.components.C_HTTPAppSocket;
import org.nhl.spoderpod.hexapod.components.C_HTTPFormat;
import org.nhl.spoderpod.hexapod.components.C_RouterClient;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * @author Fre-Meine Fuckboys
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		Service s = new Service("MovementService", new I_Component[] {
				new C_HTTPAppSocket("AppSocket", 8080),
				new C_HTTPFormat("Formatter"),
				new C_RouterClient("RouterClient","127.0.0.1",1234)});
		s.start();

	}
}