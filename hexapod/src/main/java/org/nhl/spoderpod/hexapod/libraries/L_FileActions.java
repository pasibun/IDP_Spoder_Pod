package org.nhl.spoderpod.hexapod.libraries;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class L_FileActions {

	public static List<Byte> read(){
		Path path = Paths.get("test.txt");
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return turnList(data);
	}
	

	private static List<Byte> turnList(byte[] data){
		List<Byte> awesomeSauce = new ArrayList<Byte>();
		for(int i = 0; i < data.length; i++){
			awesomeSauce.add(data[i]);
		}
		return awesomeSauce;
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
		    FileOutputStream fs = new FileOutputStream(new File("/dev/ttyAMA0"));
		    bs = new BufferedOutputStream(fs);
		    bs.write(turnArray(message));
		    bs.close();
		    fs.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
