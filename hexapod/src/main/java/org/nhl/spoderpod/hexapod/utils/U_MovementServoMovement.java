package org.nhl.spoderpod.hexapod.utils;

import org.nhl.spoderpod.hexapod.libraries.L_Encoder;
import org.nhl.spoderpod.hexapod.libraries.L_FileActions;

public final class U_MovementServoMovement {
	private final U_MovementSpoderLeg[] legArray;
	public static final int LEFT_SIDE = 1;
	public static final int RIGHT_SIDE = -1;

	public U_MovementServoMovement() {

		this.legArray = new U_MovementSpoderLeg[] {
				new U_MovementSpoderLeg(10, CreateServos(10, 135, 180, 360), LEFT_SIDE),
				new U_MovementSpoderLeg(20, CreateServos(20, 180, 180, 360), LEFT_SIDE),
				new U_MovementSpoderLeg(30, CreateServos(30, 225, 180, 360), LEFT_SIDE),
				new U_MovementSpoderLeg(40, CreateServos(40, 135, 180, 0), RIGHT_SIDE),
				new U_MovementSpoderLeg(50, CreateServos(50, 180, 180, 0), RIGHT_SIDE),
				new U_MovementSpoderLeg(60, CreateServos(60, 225, 180, 0), RIGHT_SIDE) };
	}

	private static U_MovementServo[] CreateServos(int id, int offset1, int offset2,
			int offset3) {
		return new U_MovementServo[] { new U_MovementServo(id + 1, offset1, 180),
				new U_MovementServo(id + 2, offset2, 180),
				new U_MovementServo(id + 3, offset3, 180) };
	}

	public void updateLeg(int id, int x, int y, int z) {
		legArray[id].updateServos(x, y, z);
	}

	public void sendPacket() {
		L_Encoder.reset();
		for (int n = 0; n < legArray.length; n++) {
			legArray[n].sendPacket();
		}
		L_Encoder.prepMsg((byte) 0);
		L_FileActions.write(L_Encoder.getMsgs());
	}
}
