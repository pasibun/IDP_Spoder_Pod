package org.nhl.spoderpod.hexapod.utils;

import java.util.HashMap;
import java.util.Map;

import org.nhl.spoderpod.hexapod.interfaces.I_Threaded;
import org.nhl.spoderpod.hexapod.libraries.L_Encoder;
import org.nhl.spoderpod.hexapod.libraries.L_FileActions;
import org.nhl.spoderpod.hexapod.libraries.L_Vector;

public final class U_MovementServoMovement implements I_Threaded {
	private final U_MovementSpoderLeg[] legArray;
	public static final int LEFT_SIDE = 1;
	public static final int RIGHT_SIDE = -1;
	private final Thread thread;
	private final Map<String, U_MovementCsvReader> movements;
	private U_MovementCsvReader currentMovement;

	public U_MovementServoMovement() {

		this.legArray = new U_MovementSpoderLeg[] {
				new U_MovementSpoderLeg(10, CreateServos(10, 135, 180, 360),
						LEFT_SIDE),
				new U_MovementSpoderLeg(20, CreateServos(20, 180, 180, 360),
						LEFT_SIDE),
				new U_MovementSpoderLeg(30, CreateServos(30, 225, 180, 360),
						LEFT_SIDE),
				new U_MovementSpoderLeg(40, CreateServos(40, 135, 180, 0),
						RIGHT_SIDE),
				new U_MovementSpoderLeg(50, CreateServos(50, 180, 180, 0),
						RIGHT_SIDE),
				new U_MovementSpoderLeg(60, CreateServos(60, 225, 180, 0),
						RIGHT_SIDE) };

		this.thread = new Thread(this);
		this.movements = new HashMap<String, U_MovementCsvReader>();
		movements.put("Walkstate", new U_MovementCsvReader());
		movements.put("Crabwalk", new U_MovementCsvReader());
		movements.put("Idle", new U_MovementCsvReader());
		movements.get("Walkstate").read("StraightWalk.csv");
		movements.get("Crabwalk").read("CrabWalk.csv");
		movements.get("Idle").read("StraightWalk.csv");
		this.currentMovement = movements.get("Idle");
	}

	private static U_MovementServo[] CreateServos(int id, int offset1,
			int offset2, int offset3) {
		return new U_MovementServo[] {
				new U_MovementServo(id + 1, offset1, 180),
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

	public void setCurrentMovement(String strCSVname){
		this.currentMovement = movements.get(strCSVname);
	}
	
	@Override
	public void run() {
		if (this.thread != Thread.currentThread()) {
			return;
		}
		for (int time = 0;; time++) {
			for (int n = 0; n < 6; n++) {
				L_Vector v = currentMovement.getLeg(n + 1)[time % (currentMovement.getLegCount() - 1)];
				updateLeg(n, v.x, v.y, v.z);
			}
			sendPacket();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void start() {
		this.thread.start();
	}

	@Override
	public void stop() {
		this.thread.interrupt();

	}
}
