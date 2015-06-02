package org.nhl.spoder.hexapod.movementservice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/***
 * Reads the CSV
 * @author Yannick the yellow submarine
 *
 */
public class CsvReader {
	
	String[] angles;
	
	public int[] read() {
		String csvFile = "C:/Users/Yannick/Documents/spidergap.csv";		
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ";";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			System.out.println("x      y      z");
			int id = 1;
			while ((line = br.readLine()) != null) {
				angles = line.split(csvSplitBy);
				int i = 0;
				
				System.out.println(id + ": " + angles[i] + "|" + angles[i + 1] + "|"
								 + angles[i + 2] + "|" + angles[i + 3] + "|"
								 + angles[i + 4] + "|" + angles[i + 5] + "|"
								 + angles[i + 6] + "|" + angles[i + 7] + "|"
								 + angles[i + 8] + "|" + angles[i + 9] + "|"
								 + angles[i + 10] + "|" + angles[i + 11] + "|"
								 + angles[i + 12] + "|" + angles[i + 13] + "|"
								 + angles[i + 14] + "|" + angles[i + 15] + "|"
								 + angles[i + 16] + "|" + angles[i + 17] + "|"
								 + angles[i + 18] + "|" + angles[i + 19] + "|"
								 + angles[i + 20] + "|" + angles[i + 21] + "|"
								 + angles[i + 22] + "|" + angles[i + 23]);
				
				id++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		int[] angles2 = new int[angles.length];
		for(int i = 0; i < angles.length; i++)
		{ 
		
		    angles2[i] = (int) Double.parseDouble(angles[i]);
		}
		
		System.out.println("Done");
		return angles2 ;
	}
}
