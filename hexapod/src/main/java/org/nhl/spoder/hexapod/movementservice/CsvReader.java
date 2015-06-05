package org.nhl.spoder.hexapod.movementservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/***
 * Reads the CSV
 * @author Yannick the yellow submarine
 *
 */
public class CsvReader {

	private final Map<Integer, Vector[]> leglist;

	public CsvReader() {
		this.leglist = new HashMap<Integer, Vector[]>();
	}

	private void add(int id, int time, Vector vector) {
		if (!leglist.containsKey(id)) {
			leglist.put(id, new Vector[43]);
		}
		leglist.get(id)[time - 1] = vector;
	}
	
	public Vector[] getLeg(int id) {
		if (leglist.containsKey(id)) {
			return leglist.get(id);
		}
		return new Vector[0];
	}

	public boolean read(String csvFile) {
		String line = "";
		String csvSplitBy = ";";

		String[] splittedline;

		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				splittedline = line.split(csvSplitBy);

				add(Integer.parseInt(splittedline[0]), Integer.parseInt(splittedline[1]), new Vector(
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
