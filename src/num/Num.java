package num;

public class Num {

	public static void main(String[] args) {
		String a = "42342";
//		String b = "23423";
		// System.out.println(analysis(a, b));
		
		StringBuffer d = new StringBuffer(a);
		for (int i = 0; i < 2423; i++) {
			System.out.println(d=analysis(d.toString(), a));
		}
	}

//	public static void main2(String[] args) {
//		for (int i = 0; i < 4325532; i++) {
//			String a = ((Math.random() + "a").hashCode() + "").replace("-", "").replace(".", "");
//			String b = ((Math.random() + "c").hashCode() + "").replace("-", "").replace(".", "");
//			// System.out.println(analysis(a, b));
//			long n = Long.parseLong(analysis(a, b).toString());
//			long sum = Long.parseLong(a) + Long.parseLong(b);
//			if (n != sum) {
//				System.out.println(a + ":" + b);
//				System.out.println(n + "!=" + sum);
//			}
//		}
//	}

	private static StringBuffer analysis(String a, String b) {
		char[] as = a.toCharArray();
		char[] bs = b.toCharArray();
		int lastBsIndex = bs.length - 1;
		StringBuffer result = new StringBuffer();
		int storage = 0;
		for (int i = as.length - 1; i > -1; i--) {
			// System.out.println(as[i]);
			int endInt = 0;
			if (lastBsIndex > -1) {
				endInt = charToInt(bs[lastBsIndex]);
			}
			int appendNumber = charToInt(as[i]) + endInt + storage;
			if (appendNumber >= 10) {
				storage = (appendNumber / 10);
				appendNumber %= 10;
			} else {
				storage = 0;
			}
			result.append(appendNumber);
			lastBsIndex--;
		}
		for (; lastBsIndex > -1; lastBsIndex--) {
			int appendNumber = charToInt(bs[lastBsIndex]) + storage;
			if (appendNumber >= 10) {
				storage = (appendNumber / 10);
				appendNumber %= 10;
			} else {
				storage = 0;
			}
			result.append(appendNumber);
		}
		if (storage != 0) {
			result.append(storage);
		}
		return result.reverse();
	}

	private static int charToInt(char in) {
		return Integer.parseInt(in + "");
	}

}
