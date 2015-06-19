package org.nhl.spoderpod.hexapod.utils;

import java.util.Arrays;
/***
 * damn dude
 * @author achmed
 *
 */
import org.nhl.spoderpod.hexapod.libraries.L_Calculations;
import org.nhl.spoderpod.hexapod.libraries.L_Encoder;

public final class U_MovementSpoderLeg {
	private final int legId;
	private final U_MovementServo[] servoArray;
	private final int forceMultiplier;

	public U_MovementSpoderLeg(int id, U_MovementServo[] servoArray,
			int forceMultiplier) {
		this.legId = id;
		this.servoArray = servoArray;
		this.forceMultiplier = forceMultiplier;
	}

	public int getLegId() {
		return this.legId;
	}

	public void updateServos(int x, int y, int z) {
		servoArray[0].setServo(servoArray[0].getOffset()
				+ L_Calculations.InsideServo(x, y, z) * this.forceMultiplier);
		servoArray[1].setServo(servoArray[1].getOffset()
				+ L_Calculations.MiddleServo(x, y, z) * this.forceMultiplier);
		servoArray[2].setServo(servoArray[2].getOffset()
				- L_Calculations.OutsideServo(x, y, z) * this.forceMultiplier);
	}

	@Override
	public String toString() {
		return String.format("%s", Arrays.toString(servoArray));
	}

	public void sendPacket() {
		for (int n = 0; n < servoArray.length; n++) {
			L_Encoder.addData((byte) 1, (byte) (legId + n + 1),
					(short) servoArray[n].getPosition());
		}
	}
}
