package org.nhl.spoderpod.hexapod.libraries;

import java.util.ArrayList;
import java.util.List;

public class L_Encoder {

	private static List<Byte> byteMsgs = new ArrayList<Byte>();
	
	public static void addData(byte type, byte id, short data){
		byteMsgs.add((byte) (type & 0xff) ); //first of messages
		byteMsgs.add((byte) (id & 0xff));
		byteMsgs.add((byte) (data & 0xff));
		byteMsgs.add((byte) ((data >> 8) & 0xff ));
	}
	
	/***
	 * sum all bytes, overload is checksum. Getal boven de 256 is de checksum. 
	 * @return
	 */
	public static void checkSum(){
		byte x = 0;
		for(byte b : byteMsgs){
			x += b; 
		}
		byteMsgs.add(0, x);
	}
	
	public static void sendMsg(){
		checkSum();
		addDestination((byte)0);
		COBS();
		addZero();
		addZero();
	}
	
	public static void addDestination(byte destination){
		byteMsgs.add(1, (byte) (destination & 0xff));
	}
	
	public static void  addZero(){
		byteMsgs.add((byte) 0);
	}
	
	public static void COBS(){	
		byte i = (byte) (byteMsgs.size()+1);
		for(byte b:byteMsgs){
			i++;
			if(b == 0){
				b = i;
				i = 0;
			}
		}
	}
	
	
}
