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
	static InputStream inputstream = Utils.CreateFileinput("ttyAMA0");
	
	public static List<Byte> read() throws IOException {
		List<Byte> awesomeSauce = new ArrayList<Byte>();
		int data;
		while (((data = inputstream.read()) & 0xFF) != 0) {
			awesomeSauce.add((byte) data);
		}
		return awesomeSauce;
		// Path path = Paths.get("ttyAMA0");
		// byte[] data = null;
		// try {
		// data = Files.readAllBytes(path);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
		
	private static byte[] turnArray(List<Byte> winList){
		byte[] awesomeSauce = new byte[winList.size()];
		for(int i = 0; i < winList.size(); i++){
			awesomeSauce[i] = winList.get(i);
		}
		return awesomeSauce;
	}
	
	public static void write(List<Byte> message){
		BufferedOutputStream bs = null;
		try {
			//"/dev/ttyAMA0"
		    FileOutputStream fs = new FileOutputStream(new File("ttyAMA01"));
		    bs = new BufferedOutputStream(fs);
		    bs.write(turnArray(message));
		    bs.close();
		    fs.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
