package org.nhl.spoder.hexapod.httpservice;

import org.nhl.spoderpod.hexapod.components.CLogger;
import org.nhl.spoderpod.hexapod.components.CRandomTalker;
import org.nhl.spoderpod.hexapod.components.CServerListener;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

/***
 * HTTP Service verstuurt de gegevens naar de server van de webapp.
 * 
 * 
 * @author Driving Ghost
 */
public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("HTTP Service started.");
		Refactor r = new Refactor();
		r.run();
	}
}