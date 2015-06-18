package org.nhl.spoderpod.hexapod.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.nhl.spoderpod.hexapod.libraries.L_Vector;

/***
 * Reads the CSV
 * 
 * @author Yannick the yellow submarine
 *
 */
public class U_MovementCsvReader {
	private final Map<Integer, L_Vector[]> leglist;
	private final int legCount;

	public U_MovementCsvReader(File file) {
		this.leglist = ReadCSV(file);
		this.legCount = CalcMinLegcount(this.leglist);
	}

	private static int CalcMinLegcount(Map<Integer, L_Vector[]> leglist) {
		int count = Integer.MAX_VALUE;
		for (L_Vector legs[] : leglist.values()) {
			if (legs.length < count) {
				count = legs.length;
			}
		}
		return count;
	}

	public L_Vector[] getLeg(int id) {
		if (leglist.containsKey(id)) {
			return leglist.get(id);
		}
		return null;
	}

	public int getLegCount() {
		return legCount;
	}

	private static Map<Integer, L_Vector[]> ReadCSV(File csvFile) {
		String line, csvSplitBy = ";";
		String[] splittedline;
		List<Entry> entries = new ArrayList<>();
		Map<Integer, L_Vector[]> result = new HashMap<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				splittedline = line.split(csvSplitBy);
				entries.add(new Entry(Integer.parseInt(splittedline[0]),
						new L_Vector(Integer.parseInt(splittedline[1]),
								(int) Double.parseDouble(splittedline[2]),
								(int) Double.parseDouble(splittedline[3]),
								(int) Double.parseDouble(splittedline[4]))));
			}
			br.close();
		} catch (Exception e) {
		}

		Set<L_Vector> tmp = new TreeSet<L_Vector>(new Comparator<L_Vector>() {
			public int compare(L_Vector o1, L_Vector o2) {
				return o1.time - o2.time;
			};
		});
		while (entries.size() > 0) {
			int id = entries.get(entries.size() - 1).id;
			for (int n = entries.size() - 1; n >= 0; --n) {
				if (entries.get(n).id == id) {
					tmp.add(entries.get(n).leg);
					entries.remove(n);
				}
			}
			result.put(id, tmp.toArray(new L_Vector[tmp.size()]));
			tmp.clear();
		}
		return result;
	}

	private static class Entry {
		public final int id;
		public final L_Vector leg;

		public Entry(int id, L_Vector vector) {
			this.id = id;
			this.leg = vector;
		}
	}
}
