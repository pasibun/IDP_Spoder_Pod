package org.nhl.spoder.hexapod.vision;

import org.nhl.spoderpod.hexapod.components.C_RouterClient;
import org.nhl.spoderpod.hexapod.components.C_VisionFormatter;
import org.nhl.spoderpod.hexapod.components.C_VisionListener;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.I_Component;

/***
 * Vision Service verzorgt het versturen van data naar de AI service, zodat die een berekening kan maken. 
 * 
 * @author Driving Ghost
 * TODO Matthijs, waar komt de data in VisionFormatter, die naar de AI service en logger wordt gestuurd, vandaan?
 * TODO Kan VisionListner weg?
 *
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Started service");


		Service s = new Service("Vision", new I_Component[] {
				new C_VisionFormatter("C_VisionFormatter"),
				new C_VisionListener("Vision"),
				new C_RouterClient("RouterClient", "127.0.0.1", 1234) });
		s.start();
		Thread.sleep(10 * 1000);
		s.stop();

	}
}

/*

*/