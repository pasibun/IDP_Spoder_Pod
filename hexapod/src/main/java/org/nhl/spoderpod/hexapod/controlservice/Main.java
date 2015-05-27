package org.nhl.spoderpod.hexapod.controlservice;

import org.nhl.spoderpod.hexapod.components.C_ControlCheck;
import org.nhl.spoderpod.hexapod.core.Service;
import org.nhl.spoderpod.hexapod.interfaces.IComponent;

/***
 * Welcome to the ControlState Service service commentarty. 
 * Written by Hidde Westerhof, complaints given by Yannick Strobl cause he touches himself at night. 
 * Are you really reading this teachers? I doubt it. Please send an email to Obama@NSA.Gov.us to prove it. 
 * 
 * Anyways.
 * ConstrolState service decides wether or not the spider's actions are decided by itself or by the HumanInteraction-
 * Service. This is usefull for autonomous activities and actions performed by the spider, and can be seen as the first
 * step of a failsafe. 
 * 
 * @todo Add RouterClient component referrences when it is done.
 * @todo possibly add updates when more services are added. 
 * @todo INTERGRATION
 * @version Alpha
 * @author Yannick
 *
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Started service");
		Service s = new Service("ControlService", new IComponent[] { 
																	new C_ControlCheck("ControlState")
																	});
	
		s.start();
		
	}	
}
