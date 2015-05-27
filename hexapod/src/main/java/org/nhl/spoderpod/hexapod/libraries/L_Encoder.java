package org.nhl.spoderpod.hexapod.libraries;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

public class L_Encoder {

	private static List<Byte> byteMsgs = new ArrayList<Byte>();
	private int[] indexZeroLocations;
	
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
		int x =0;
		for(byte b : byteMsgs){
			x += b; 
		}
		byteMsgs.add(0, (byte) x);
	}
	
	public static void sendMsg(){
		checkSum();
		recentZero();
		addZero();
		addZero();
	}
	
	public static void addDestination(byte destination){
		byteMsgs.add(1, (byte) (destination & 0xff));
	}
	
	public static void  addZero(){
		byteMsgs.add((byte) 0);
	}
	
	public static void recentZero(){	
		int i = 0;
		for(byte b:byteMsgs){
			i++;
			if(b == 0){
				b = (byte) i;
				i = 0;
			}
		}
	}
	
	
}
