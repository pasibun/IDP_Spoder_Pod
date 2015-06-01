package org.nhl.spoderpod.hexapod.libraries;

import java.util.ArrayList;
import java.util.List;

public class L_Encoder {

	private static List<Byte> byteMsgs = new ArrayList<Byte>();

	public static void addData(byte type, byte id, short data) {
		byteMsgs.add((byte) (type & 0xff)); // first of messages
		byteMsgs.add((byte) (id & 0xff));
		byteMsgs.add((byte) (data & 0xff));
		byteMsgs.add((byte) ((data >> 8) & 0xff));
	}

	/***
	 * sum all bytes, overload is checksum. Getal boven de 256 is de checksum.
	 * 
	 * @return
	 */
	public static void checkSum() {
		byte x = 0;
		for (byte b : byteMsgs) {
			x += b;
		}
		byteMsgs.add(0, x);
	}

	/***
	 * After adding all the data that is needed, use this method to prep the
	 * message for sending.
	 * 
	 * @param destination
	 *            location that it needs to go.
	 */
	public static void prepMsg(byte destination) {
		addDestination(destination);
		COBS();
		addZero();
		checkSum();
	}

	public static void addDestination(byte destination) {
		byteMsgs.add(0, (byte) (destination & 0xff));
	}

	public static void addZero() {
		byteMsgs.add((byte) 0);
	}

	private static void COBS() {
		int z = 0;
		for(int i = 0; i < byteMsgs.size(); i++){
			z++;
			if (byteMsgs.get(i) == 0) {
				byteMsgs.set(i, (byte) (z & 0xff));
				z = 0;
			}
		}
		if(z == byteMsgs.size()){
			z = 0;
		}
		byteMsgs.add((byte) (z & 0xff));
	}

	public static List<Byte> getMsgs() {
		return byteMsgs;
	}

}
