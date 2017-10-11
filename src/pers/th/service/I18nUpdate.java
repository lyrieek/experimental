package pers.th.service;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
public class I18nUpdate {

	static final String charset = "UTF-8";

	public static Map<String, String> maps = new HashMap<>();

	public static void main(String[] args) {
		try {
			//Pattern annotationLinePattern = Pattern.compile("((//)|(/\\*+)|((^\\s)*\\*)|((^\\s)*\\*+/))+"
			HttpURLConnection urlcon = (HttpURLConnection) new URL("http://192.168.2.47:9000/app/i18n/locale.zh-CN.json").openConnection();
			InputStream reader = urlcon.getInputStream();
//			InputStream reader = new FileInputStream(new File("G:\\test\\lcoale.zh-CN.json.txt"));
//			int length = 0;
//			byte[] buffer = new byte[8142];
//			List<byte[]> bytes = new Vector<byte[]>();
//			while ((length = reader.read(buffer)) != -1) {
//				bytes.add(new String(buffer, 0, length, "UTF-8").getBytes(Charset.defaultCharset()));
//			}
//			reader.close();
//			StringBuffer content = new StringBuffer();
//			for (byte[] byteData : bytes) {
//				content.append(new String(byteData));
//			}
//			content.trimToSize();
//			Matcher matcher = Pattern.compile("\".+\"\\s*:\\s*\".+\"\\s*,").matcher(content);
//			while (matcher.find()) {
//				System.out.println(content.substring(matcher.start(), matcher.end() - 1));
//			}
			
			Scanner sca = new Scanner(reader,"utf-8");
			int index = 0;
			while (sca.hasNext()) {
				String line = sca.nextLine();
				Matcher matcher = Pattern.compile("\".+\"\\s*:\\s*\".+\"").matcher(line);
				if (matcher.find()) {
					index++;
					addField(line.substring(matcher.start(),matcher.end()));
				} 
			}
			sca.close();
			
			/*"
"MedicalCard":"医疗卡"
"Approval":"批核日期"
"ChineseName":"中文姓名"
"To":"至"
"NameL3":"中文名字"
"Scheme":"计划"
"CardCode":"编码"
*/
			
			
			System.out.println(index);
			System.out.println(maps.size());
//			Map<String, String> maps = new LinkedHashMap();
//			maps.put("sdfs", "324");
//			maps.put("btrdt", "122");
//			maps.put("fdbtrrr", "121");
//			maps.put("rerd", "232");
//			for (Entry<String, String> item : maps.entrySet()) {
//				System.out.println(item);
//			}
//			outJSON();
			// copy(((HttpURLConnection) new
			// URL("http://192.168.2.47:9000/app/i18n/locale.zh-HK.json").openConnection()).getInputStream(),
			// new FileOutputStream(new File("G:\\test\\json.txt"),true));
			// copy(new FileInputStream(new File("G:\\test\\json.txt")),
			// new FileOutputStream(new File("G:\\test\\json2.txt")));
			// StringBuffer content =
			// readByUrl("http://192.168.2.47:9000/app/i18n/locale.zh-CN.json");
			// writeOutFile("G:\\test\\json.txt",content);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void addField(String field) {
		int splitPoint = 0, startPoint = 0;
		while ((splitPoint = field.indexOf(":", startPoint)) != -1) {
			String key = field.substring(0, splitPoint).trim();
			String value = field.substring(splitPoint + 1).trim();
			String wrap = "\"";
			if (key.startsWith(wrap) && key.endsWith(wrap) && !key.endsWith("\\" + wrap) && value.startsWith(wrap)
					&& value.endsWith(wrap) && key.length() > 2 && value.length() > 2) {
				if (maps.get(key.substring(1, key.length() - 1)) != null) {
					System.out.println(field);
				}
				maps.put(key.substring(1, key.length() - 1), value.substring(1, value.length() - 1));
				return;
			} 
			startPoint = splitPoint + 1;
		}
		System.out.println(field);
	}

	private static void outJSON() {
		try {
			Map<String, String> json = new HashMap<String, String>();
			json.put("abc", "123");
			json.put("sdf", "234");
			json.put("wer", "456");
			RandomAccessFile fw = new RandomAccessFile("G:\\test\\lcoale.en-US.json.txt", "rws");
//			FileWriter fw = new FileWriter(new File("G:\\test\\lcoale.en-US.json.txt"));
			String templateJson = "\t\"${key}\":\"${value}\"";
			writeLine(fw,"{");
			writeLine(fw);
			Set<String> sets = json.keySet();
			Iterator<String> item = sets.iterator();
			while (!sets.isEmpty()) {
				
				String keyItem = item.next();
				String field = templateJson;
				field = field.replace("${key}", keyItem);
				field = field.replace("${value}", json.get(keyItem));
				fw.write(String.format("\t\"%s\":\"%s\"",keyItem,json.get(keyItem)).getBytes());
				if (item.hasNext()) {
					writeLine(fw,",");
					continue;
				}
				writeLine(fw);
				break;
			}
			writeLine(fw,"}");
			fw.close();
		} catch (Exception e) {
		}
	}

	private static void writeLine(RandomAccessFile fw,String... prefix) throws IOException {
		String context = "";
		if (prefix != null) {
			for (int i = 0; i < prefix.length; i++) {
				context += prefix[i];
			}
		}
		context += System.lineSeparator();
		fw.write(context.getBytes());
	}

	public static void copy(InputStream reader, OutputStream writer) throws Exception {
		int length = 0;
		byte[] buffer = new byte[0x300000];
		while ((length = reader.read(buffer)) != -1) {
			writer.write(buffer, 0, length);
			writer.flush();
		}
		reader.close();
		writer.close();
	}

	public static void writeOutFile(String string, StringBuffer content) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File("G:\\test\\json.txt"), true);
		fos.write(content.toString().getBytes(charset));
		fos.flush();
		fos.close();
	}

	public static StringBuffer readByUrl(String url) throws Exception {
		HttpURLConnection urlcon = (HttpURLConnection) new URL(url).openConnection();
		InputStream reader = urlcon.getInputStream();
		int length = 0;
		byte[] buffer = new byte[0x300000];
		StringBuffer content = new StringBuffer();
		while ((length = reader.read(buffer)) != -1) {
			content.append(new String(buffer, 0, length, charset));
		}
		System.out.println("read ok");
		return content;
	}

}
