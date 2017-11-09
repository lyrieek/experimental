package pers.th.chars;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class WordBase {
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			Random rand = new Random(1000 * i);
			System.out.println(rand.nextInt(1024));
		}
        System.out.println(randomString(-229985452)+' '+randomString(-147909649));
    }

    public static String randomString(int seed) {
        Random rand = new Random(seed);
        StringBuffer sb = new StringBuffer();
        while(true) {
            int n = rand.nextInt(27);
            if (n == 0) break;
            sb.append((char) ('`' + n));
        }
        return sb.toString();
    }

	
//	public static void main(String[] args) {

//		for (int i = 0; i < 120; i++) {
//
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					synchronized (Integer.valueOf(1)) {
//						System.out.println(1);
//					}
//				}
//			}).start();
//		}
//	}
	
	public WordBase() {
		chars = new StringBuffer();
	}

	public StringBuffer chars;

	public boolean begin(String prefix) {
		return chars.toString().startsWith(prefix);
	}

	public boolean end(String suffix) {
		return chars.toString().endsWith(suffix);
	}

	public boolean isEmpty() {
		return chars == null || chars.length() == 0;
	}

	public boolean between(String prefix, String suffix) {
		return begin(prefix) && end(suffix);
	}

	public boolean isInt() {
		return matches("\\d+");
	}

	public boolean matches(String regex) {
		return chars.toString().matches(regex);
	}

	public int toInt() {
		return Integer.parseInt(chars.toString());
	}

	public long toLong() {
		return Long.parseLong(chars.toString());
	}

	/**
	 * find one result true
	 * all not find or param is null result false
	 * @param param
	 * @return
	 */
	public boolean contains(String... param) {
		if (param == null) {
			return false;
		}
		for (String item : param) {
			if (chars.toString().contains(item)) {
				return true;
			}
		}
		return false;
	}

	public WordBase clear() {
		chars.setLength(0);
		return this;
	}

	public int findCount(String param) {
		int begin = 0, indexOf, count = 0;
		while ((indexOf = chars.indexOf(param, begin)) != -1) {
			count++;
			begin = indexOf + param.length();
		}
		return count;
	}

	public int indexOf(String... content) {
		int findPoint = -1;
		for (String item : content) {
			findPoint = chars.indexOf(item);
			if (findPoint != -1) {
				return findPoint;
			}
		}
		return findPoint;
	}

	public int indexOf(String content, int begin) {
		return chars.indexOf(content, begin);
	}
	
	public String toBase64() {
		return Base64.encodeBase64(chars.toString());
	}
	
	public Process execution() {
		try {
			return Runtime.getRuntime().exec(chars.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean createFile() {
		boolean flag = false;
		try {
			if (chars.equals("")) {
				return flag;
			}
			String initContext = "";
			String fileName = chars.toString();
			int findPoint = indexOf(" ", ",", "-");
			if (findPoint != -1) {
				initContext = fileName.substring(findPoint + 1);
				fileName = fileName.substring(0, findPoint);
			}
			File file = new File(fileName);
			flag = file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(initContext);
			writer.flush();
			writer.close();
			flag = true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return flag;
	}


	
	@Override
	public String toString() {
		return chars.toString();
	}

	
}
