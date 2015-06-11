package org.nhl.spoderpod.hexapod.libraries;

import java.util.ArrayList;
import java.util.List;
public class L_Encoder {

	private static List<Byte> byteMsgs = new ArrayList<Byte>();

	public static void addData(byte type, byte id, short data) {
		byteMsgs.add((byte) (type & 0xff)); // first of messages
		byteMsgs.add((byte) (id & 0xff));
		byteMsgs.add((byte) ((data >> 8) & 0xff));
		byteMsgs.add((byte) (data & 0xff));
		
	}

	/***
	 * sum all bytes, overload is checksum. Getal boven de 256 is de checksum.
	 * 
	 * @return
	 */
	public static void checkSum() {
		byte x = 0;
		for (byte b : byteMsgs) {
			x += (b & 0xff);
		}
		byteMsgs.add(0, (byte) ((byte) (x & 0xff)- (byte) 75));
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
		//hele list afgaan, elke index i++.
		//als index 0 is, i erin zetten en i op 0 zetten. 
		int x = 1;
		for(int i = 0; i < byteMsgs.size(); i++){
			x++;
			if(byteMsgs.get(i) == 0){
				byteMsgs.set(i, (byte) (x & 0xff) );
				x = 0;
			}
		}
		byteMsgs.add((byte) ((x+1) & 0xff));
//		
//		int n, lastZeroByte = -2;
//		for(n = 0; n < byteMsgs.size(); n++){
//			if(byteMsgs.get(n) == 0){
//				byteMsgs.set(n, (byte) ((n - lastZeroByte) & 0xff) );
//				lastZeroByte = n;
//			}
//		}
//		byteMsgs.add((byte) ((lastZeroByte - 1) & 0xff));
	}

	public static List<Byte> getMsgs() {
		return byteMsgs;
	}

	public static void reset() {
		byteMsgs.clear();
	}
}
