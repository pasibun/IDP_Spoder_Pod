package org.nhl.spoderpod.hexapod.libraries;

import java.util.ArrayList;
import java.util.List;

import org.nhl.spoderpod.hexapod.core.DataPackage;

/**
 * This class is for decoding the incomming message. Checking the checksum,
 * replacing zero bytes en devide the message.
 * 
 * @author matthijs_laptop
 *
 */
public class L_Decoder {
	private static byte type;
	private static byte id;
	private static short data;
	private static byte checkSum;
	private static byte destination;
	private static List<Byte> dataMsg = new ArrayList<Byte>();
	private static List<DataPackage> contents = new ArrayList<DataPackage>();

	public static void recieveMsg(List<Byte> msg) {
		contents.clear();
		dataMsg = msg;
		decodeCOBS(dataMsg);
		if (getCheckSum(dataMsg)) {
			readMessage(dataMsg);
		}
	}

	/***
	 * sum all bytes, overload is checksum.
	 * 
	 * @return
	 */
	private static boolean getCheckSum(List<Byte> msg) {
		byte x = 0;
		for (int i = 1; i < msg.size(); i++) {
			x += msg.get(i);

		}
		checkSum = (byte) (x - 75);
		if (x != msg.get(0)) {
			return false;
		}
		return true;
	}

	/**
	 * Decode the incomming message. Replace the 0 bytes with something..
	 * 
	 * @param msg
	 */
	private static void decodeCOBS(List<Byte> msg) {
		int n;
		int lastZeroByte = msg.size() - 1;
		while (lastZeroByte > -1) {
			n = lastZeroByte;
			if (lastZeroByte < msg.size()) {
				lastZeroByte -= msg.get(lastZeroByte);
				msg.set(n, (byte) 0);
			} else {
				System.out.println("Error, breaking");
				break;
			}
		}
	}

	/**
	 * Message subdevide
	 * 
	 * @param msg
	 */
	private static void readMessage(List<Byte> msg) {
		for (int i = 2; i < msg.size(); i += 4) {
			if (msg.size() - 1 == i) {
				break;
			}
			type = msg.get(i);
			id = msg.get(i + 1);
			data = (short) ((msg.get(i + 2) << 8) | (msg.get(i + 3)) & 0xFF);
			contents.add(new DataPackage(type, id, data));
		}
	}

	public static List<DataPackage> getData() {
		return contents;
	}

}