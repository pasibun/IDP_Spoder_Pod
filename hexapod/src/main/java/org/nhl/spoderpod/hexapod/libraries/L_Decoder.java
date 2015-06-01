package org.nhl.spoderpod.hexapod.libraries;

import java.util.List;

public class L_Decoder {
	private static byte type;
	private static byte id;
	private static short[] data;
	private static byte checkSum;
	private static byte destination;

	public static void recieveMsg(List<Byte> msg) {
		DecodeCOBS(msg);
		boolean check = getCheckSum(msg);
		if (check == true)
			readMessage(msg);
	}

	/***
	 * sum all bytes, overload is checksum.
	 * 
	 * @return
	 */
	private static boolean getCheckSum(List<Byte> msg) {
		byte x = 0;
		for (byte b : msg) {
			x += b;
		}
		checkSum = x;
		if (x != msg.get(0)) {
			return false;
		}
		return true;
	}

	private void findDestination(List<Byte> msg) {
		destination = msg.get(1);
	}

	private static void DecodeCOBS(List<Byte> msg) {
		int n;
		int lastZeroByte = msg.size();
		while (lastZeroByte <= msg.size()) {
			n = lastZeroByte;
			lastZeroByte -= msg.get(lastZeroByte);
			msg.set(n, (byte) 0);
		}
	}

	private static void readMessage(List<Byte> msg) {
		for (int i = 2; i < msg.size(); i += 4) {
			if (msg.size() - 2 == i) {
				break;
			}
			type = msg.get(i + 1);
			id = msg.get(i + 2);
			data[0] = msg.get(i + 3);
			data[1] = msg.get(i + 4);
		}
	}

}
