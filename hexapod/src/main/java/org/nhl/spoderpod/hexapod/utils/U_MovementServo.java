package org.nhl.spoderpod.hexapod.utils;

public class U_MovementServo {
	private final int servoId;
	private final int offset;
	private int position;
	
	public U_MovementServo(int servoId, int offset, int position){
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
