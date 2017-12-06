package pers.th;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomAnalysis {

	private static final long multiplier = 0x5DEECE66DL;
	private static final long addend = 0xBL;
	private static final long mask = (1L << 48) - 1;
	private static long seedValue = 1024;
	private static int bits = 32;

	public static void main(String[] args) throws Exception {
		RandomAnalysis ra = new RandomAnalysis();
		// ra.verifyChar("T4u'`Ce", 10000000, 200, 700, 7);
		// ra.printfChars(10000000, 200, 700, 7);
//		Map<Integer, List<String>> pws = ra.printfChars(100000000, 0, 1000, 12, 7);
//		for (Entry<Integer, List<String>> item : pws.entrySet()) {
//			System.out.print(item.getKey() + ":");
//			for (String value : item.getValue()) {
//				System.out.print(value + " ");
//			}
//			System.out.println();
//		}
		boolean flag = ra.verif(0, 1000, 100000000, "Br)Z'P5dG>wa", 516);
		System.out.println(flag);
		// ra.printfChars(102410241024L, 0, 1000, 12);
	}

	/**
	 * 
	 * @param seed
	 *            > 100000000
	 * @param start
	 *            0
	 * @param end
	 *            1000
	 * @param length:
	 *            field length
	 * @return map,integer is key,list is result
	 */
	public Map<Integer, List<String>> printfChars(long seed, int start, int end, int length, int size) {
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

	// new StringBuffer().insert(0, "1");
	// for (char c : "104101108108111032119111114108100".toCharArray()) {
	// if (count++ % 3 == 0) {
	// System.out.println(c);
	// }
	// }
	// for (String item :
	// "104,101,108,108,111,032,119,111,114,108,100".split(",")) {
	// System.out.print((char)Integer.parseInt(item));
	// }
	// System.out.println("104,101,108,108,111,32,119,111,114,108,100".split(","));
	// for (int i = 0; i < 100; i++) {
	// System.out.println(nextInt(i));
	// }
	// printPoint("hello world");

	/**
	 * ��Χ��̽
	 * 
	 * @param hashCodes
	 *            ���еĹ�ϣ
	 * @param count
	 *            ��Ҫ���ɶ��ٿ�������
	 */
	public void scanner(StringBuffer hashCodes, int count) {
		for (int i = 0; i < 999; i++) {
			Matcher m = Pattern.compile(i + "").matcher(hashCodes.toString());
			int n = 0;
			while (m.find()) {
				n++;
			}
			if (n == count) {
				System.out.println(i + ":" + n);
			}
		}
	}

	public void verifyChar(String string, int count, int start, int end, int length) {
		for (int i = start; i < end; i++) {
			Random rand = new Random(count + i);
			char[] chars = new char[length];
			chars[0] = (char) (rand.nextInt(90 - 65) + 65);
			for (int j = 1; j < length - 1; j++) {
				chars[j] = (char) (rand.nextInt(126 - 35) + 35);
			}
			chars[length - 1] = (char) (rand.nextInt(122 - 97) + 97);
			if ("T4u'`Ce".equals(new String(chars))) {
				System.out.println("ok");
				return;
			}
		}
	}

	public static void printPoint(String str) {
		char[] chars = str.toCharArray();
		System.out.print((int) chars[0]);
		if (chars.length > 1) {
			for (int i = 1; i < chars.length; i++) {
				System.out.print(",");
				System.out.print((int) chars[i]);
			}
		}
		System.out.println();
	}

	public static void printfBasic() {
		int line = 15;
		for (int i = 30; i <= 126; i += line) {
			for (int j = 0; j < line && i + j <= 126; j++) {
				printf(i + j);
			}
			System.out.println();
		}
	}

	private static void printf(int i) {
		System.out.print(i + ":" + (char) i + "\t");
	}

	public static int nextInt(int bound) {
		bits = 31;
		int r = next();
		int m = bound - 1;
		if ((bound & m) == 0)
			r = (int) ((bound * (long) r) >> 31);
		else {
			for (int u = r; u - (r = u % bound) + m < 0; u = next())
				;
		}
		return r;
	}

	static int next() {
		AtomicLong seed = new AtomicLong((seedValue ^ multiplier) & mask);
		long oldseed, nextseed;
		do {
			oldseed = seed.get();
			nextseed = (oldseed * multiplier + addend) & mask;
		} while (!seed.compareAndSet(oldseed, nextseed));
		return (int) (nextseed >>> (48 - bits));
	}

}
