package org.nhl.spoder.hexapod.movementservice;

public final class SpoderLeg {
	private final int legId;
	private int x, y, z;
	
	public SpoderLeg(int id, int x, int y, int z){
		this.legId = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
