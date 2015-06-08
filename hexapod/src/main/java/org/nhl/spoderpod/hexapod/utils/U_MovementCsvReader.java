package org.nhl.spoderpod.hexapod.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.nhl.spoderpod.hexapod.libraries.L_Vector;

/***
 * Reads the CSV
 * @author Yannick the yellow submarine
 *
 */
public class U_MovementCsvReader {

	private final Map<Integer, L_Vector[]> leglist;

	public U_MovementCsvReader() {
		this.leglist = new HashMap<Integer, L_Vector[]>();
	}

	private void add(int id, int time, L_Vector vector) {
		if (!leglist.containsKey(id)) {
			leglist.put(id, new L_Vector[43]);
		}
		leglist.get(id)[time - 1] = vector;
	}
	
	public L_Vector[] getLeg(int id) {
		if (leglist.containsKey(id)) {
			return leglist.get(id);
		}
		return new L_Vector[0];
	}

	public boolean read(String csvFile) {
		String line = "";
		String csvSplitBy = ";";

		String[] splittedline;

		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				splittedline = line.split(csvSplitBy);

				add(Integer.parseInt(splittedline[0]), Integer.parseInt(splittedline[1]), new L_Vector(
						(int) Double.parseDouble(splittedline[2]), (int) Double.parseDouble(splittedline[3]), (int) Double.parseDouble(splittedline[4])));

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
