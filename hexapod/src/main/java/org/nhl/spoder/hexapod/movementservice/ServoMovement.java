package org.nhl.spoder.hexapod.movementservice;

public final class ServoMovement {
	private final SpoderLeg[] legArray;
	public static final int LEFT_SIDE = 1;
	public static final int RIGHT_SIDE = -1;
	
	public ServoMovement() {

		this.legArray = new SpoderLeg[] {
				new SpoderLeg(10, CreateServos(10, 135, 180, 360), LEFT_SIDE),
				new SpoderLeg(20, CreateServos(20, 180, 180, 360), LEFT_SIDE),
				new SpoderLeg(30, CreateServos(30, 225, 180, 360), LEFT_SIDE),
				new SpoderLeg(40, CreateServos(40, 135, 180, 0), RIGHT_SIDE),
				new SpoderLeg(50, CreateServos(50, 180, 180, 0), RIGHT_SIDE),
				new SpoderLeg(60, CreateServos(60, 225, 180, 0), RIGHT_SIDE)
		};
	}

	private static Servo[] CreateServos(int id, int offset1, int offset2, int offset3) {
		return new Servo[] { new Servo(id + 1, offset1, 180), new Servo(id + 2, offset2, 180), new Servo(id + 3, offset3, 180) };
	}
	
	public void updateLegs(int x, int y, int z){
		for (SpoderLeg spoderLeg : legArray) {
			spoderLeg.updateServos(x, y, z);
			System.out.println(spoderLeg);
		}
	}
}
