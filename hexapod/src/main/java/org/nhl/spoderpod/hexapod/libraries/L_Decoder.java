package org.nhl.spoderpod.hexapod.libraries;

import java.util.ArrayList;
import java.util.List;

import org.nhl.spoderpod.hexapod.core.Message;

public class L_Decoder {
	private byte type;
	private byte id;
	private short data;

	/***
	 * sum all bytes, overload is checksum. 
	 * @return
	 */
	public static void checkSum(ArrayList<Byte>msg){
		int x =0;
		for(byte b : msg){
			x += b; 
		}
		msg.add(0, (byte) x);
	}
	
	private static void DecodeCOBS(ArrayList<Byte>msg)
	{
		int n;
		int lastZeroByte = msg.size();
		while (lastZeroByte <= msg.size())
		{
			n = lastZeroByte;
			lastZeroByte -= msg.get(lastZeroByte);
			msg.set(n, (byte)0);
		}
	}
	
	private static void readMessage(ArrayList<Byte>msg)
	{
		
	}
	
}
