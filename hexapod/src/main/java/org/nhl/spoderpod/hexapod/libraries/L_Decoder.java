package org.nhl.spoderpod.hexapod.libraries;

import java.util.ArrayList;

public class L_Decoder {
	private byte type;
	private byte id;
	private short[] data;
	private byte checkSum;
	private byte destination;

	private void recieveMsg(ArrayList<Byte> msg) {
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
	private boolean getCheckSum(ArrayList<Byte> msg) {
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

	private void findDestination(ArrayList<Byte> msg) {
		destination = msg.get(1);
	}

	private void DecodeCOBS(ArrayList<Byte> msg) {
		int n;
		int lastZeroByte = msg.size();
		while (lastZeroByte <= msg.size()) {
			n = lastZeroByte;
			lastZeroByte -= msg.get(lastZeroByte);
			msg.set(n, (byte) 0);
		}
	}

	private void readMessage(ArrayList<Byte> msg) {
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
