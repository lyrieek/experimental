package chars.u;

import java.nio.charset.Charset;
import java.util.Map.Entry;

public class TestCharSet {

	static String s = "涓€";

	public static void main(String[] args) {
		for (Entry<String, Charset> source : Charset.availableCharsets().entrySet()) {
			try {
				for (Entry<String, Charset> rep : Charset.availableCharsets().entrySet()) {
					String item = new String(s.getBytes(source.getValue()), rep.getValue());
//					String item = new String(s.getBytes(rep.getValue()), source.getValue());
					if (item.contains("一")) {
						System.out.println(source.getKey() + ":" + rep.getKey() + ":" + item);
					}
				}
				// System.out.println(item.getKey() + ":" + new
				// String(s.getBytes(item.getValue()), item.getValue()));

			} catch (Exception e) {
				System.err.print(e.getMessage());
			}
		}
	}
}
