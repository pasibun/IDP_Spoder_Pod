package org.nhl.spoder.hexapod.movementservice;

public class Servo {
	private final int servoId;
	private final int offset;
	private int position;
	
	public Servo(int servoId, int offset, int position){
		this.servoId = servoId;
		this.offset = offset;
		this.position = position;
	}
	
	public void setServo(int position){
		this.position = position;
	}

	public int getOffset() {
		return this.offset;
	}
	 
	public int getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return servoId + ":" + position;
	}
}
