package pers.th.ran;

import java.util.Random;

public class Verif {
	
	public static void main(String ... args) {
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
	
    public static String randomString(int seed) {
        Random rand = new Random(seed);
        StringBuilder sb = new StringBuilder();
        while(true) {
            int n = rand.nextInt(27);
            if (n == 0) break;
            sb.append((char) ('`' + n));
        }
        return sb.toString();
    }

//	public static void main(String[] args) {
//		System.out.println(verif("516","Br)Z'P5dG>wa"));
//	}

	public static boolean verif(String id,String key) {
		for (int i = 0; i < 1000; i++) {
			Random rand = new Random(100000000 + i);
			char[] chars = new char[key.length()];
			chars[0] = (char) (rand.nextInt(90 - 65) + 65);
			for (int j = 1; j < key.length() - 1; j++) {
				chars[j] = (char) (rand.nextInt(126 - 35) + 35);
			}
			chars[key.length() - 1] = (char) (rand.nextInt(122 - 97) + 97);
			String hashCode = (Character.toChars(chars[0] + chars[key.length() - 1] * 2).hashCode()
					* rand.nextInt(chars.length)) + "";
			if (hashCode.contains(id) && new String(chars).equals(key)) {
				return true;
			}
		}
		return false;
	}
	
}
