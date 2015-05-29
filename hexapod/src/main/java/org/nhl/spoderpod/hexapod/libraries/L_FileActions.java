package org.nhl.spoderpod.hexapod.libraries;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class L_FileActions {

	public static byte[] read(){
		Path path = Paths.get("dev/ttyAMA0");
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static boolean write(List<Byte> message){
		
		try {
			FileWriter fw = new FileWriter("dev/ttyAMA0"); 
			String s = "";
			for(byte b:message){
				s += b;
			}
			fw.write(s);
		} catch (IOException e) {
			return false;
		}
		
		return true;
		
	}
	
}
