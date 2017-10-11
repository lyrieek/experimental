package pers.th.ran;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class IRandom {

	public static void main(String[] args) throws Exception {
		
		selectPwd();
		
		verifPwd("Br)Z'P5dG>wa",516);
	}
	
	public static void verifPwd(String pwd,int group) {
		System.out.println(new IRandom().verif(0, 1000, 100000000, pwd, group));
	}

	public static void selectPwd() {
		IRandom ia = new IRandom();
		Map<Integer, List<String>> pws = ia.getMaps(100000000, 0, 1000, 12, 7);
		for (Entry<Integer, List<String>> item : pws.entrySet()) {
			System.out.print(item.getKey() + ":");
			for (String value : item.getValue()) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}
	
	public Map<Integer, List<String>> getMaps(long seed, int start, int end, int length, int size) {
		int[] arr = new int[999];
		List<char[]>[] data = new List[999];
		Map<Integer, List<String>> results = new HashMap<>();
		for (int i = start; i < end; i++) {
			Random rand = new Random(seed + i);
			char[] chars = new char[length];
			chars[0] = (char) (rand.nextInt(90 - 65) + 65);
			for (int j = 1; j < length - 1; j++) {
				chars[j] = (char) (rand.nextInt(126 - 35) + 35);
			}
			chars[length - 1] = (char) (rand.nextInt(122 - 97) + 97);
			String hashCode = (Character.toChars(chars[0] + chars[length - 1] * 2).hashCode()
					* rand.nextInt(chars.length)) + "";
			for (int j = 0; j < arr.length; j++) {
				if (hashCode.contains(j + "")) {
					arr[j] = arr[j] + 1;
					if (data[j] == null) {
						data[j] = new ArrayList<>();
					}
					data[j].add(chars);
				}
			}
		}
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == size) {
				List<String> arrs = new ArrayList<>();
				for (char[] list : data[i]) {
					arrs.add(new String(list));
				}
				results.put(i, arrs);
			}
		}
		return results;
	}

	public boolean verif(int start, int end, int seed, String key, int id) {
		for (int i = start; i < end; i++) {
			Random rand = new Random(seed + i);
			char[] chars = new char[key.length()];
			chars[0] = (char) (rand.nextInt(90 - 65) + 65);
			for (int j = 1; j < key.length() - 1; j++) {
				chars[j] = (char) (rand.nextInt(126 - 35) + 35);
			}
			chars[key.length() - 1] = (char) (rand.nextInt(122 - 97) + 97);
			String hashCode = (Character.toChars(chars[0] + chars[key.length() - 1] * 2).hashCode()
					* rand.nextInt(chars.length)) + "";
			if (hashCode.contains(id + "") && new String(chars).equals(key)) {
				return true;
			}
		}
		return false;
	}

}
