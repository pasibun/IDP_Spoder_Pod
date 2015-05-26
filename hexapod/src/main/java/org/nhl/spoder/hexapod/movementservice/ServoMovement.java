package org.nhl.spoder.hexapod.movementservice;

public final class ServoMovement {
	private final SpoderLeg[] legArray;
	
	public ServoMovement(){
		this.legArray = new SpoderLeg[]{new SpoderLeg(10, 180, 180, 180)};
	}
	
	
}
