package org.nhl.spoderpod.hexapod.libraries;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.nhl.spoderpod.hexapod.utils.Utils;

public class L_FileActions {
	static FileInputStream inputstream = Utils.CreateFileInput("/dev/ttyAMA0"); /* "/dev/ttyAMA0" */
	static FileOutputStream outputstream = Utils
			.CreateFileOutput("/dev/ttyAMA0");/* "/dev/ttyAMA0" */
	static List<Byte> awesomeSauce = new ArrayList<Byte>();

	public static List<Byte> read() throws IOException {
		List<Byte> awesomeSauce = new ArrayList<Byte>();
		int data = -1;
		while (((data = inputstream.read()) & 0xFF) != 0) {
			
			awesomeSauce.add((byte) data);
		}
		
		return awesomeSauce;
	}

	private static byte[] turnArray(List<Byte> winList) {
		byte[] awesomeSauce = new byte[winList.size()];
		for (int i = 0; i < winList.size(); i++) {
			awesomeSauce[i] = winList.get(i);
		}
		return awesomeSauce;
	}

	public static void write(List<Byte> message) {
		try {
			outputstream.write(turnArray(message));
			outputstream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
