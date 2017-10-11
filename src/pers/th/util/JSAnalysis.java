package pers.th.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class JSAnalysis {

	public static String context;
	public static List<String> searchs = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		searchs.add("\"");
		searchs.add("'");
		JSAnalysis jsAnalysis = new JSAnalysis();
		String path = "js";
		context = getSource(new File(path));
		jsAnalysis.deleteNote();
		System.out.println(context);
	}

	private boolean isInString(String code) {
		StringAnalysis sa = StringAnalysis.newStringAnalysis(code);
		int lastIndex = 0;
		Searchs search;
		if (sa.trim().isEmpty() || (search = getNeedSearchs(sa, lastIndex)) == null) {
			return false;
		}
		boolean isIn = false;
		int point;
		if ((point = searchPoint(sa.toString(), search, false)) != -1) {
			isIn = !isIn;
			search.setIndex(lastIndex = point + 1);
			if ((point = searchPoint(sa.toString(), search, true)) != -1) {
				isIn = !isIn;
				search.setIndex(lastIndex = point + 1);
			}
		}
		if (!isIn && getNeedSearchs(sa, lastIndex) != null) {
			return isInString(sa.sub(lastIndex).toString());
		}
		return isIn;
	}

	public int searchPoint(String context, Searchs search, boolean need) {
		if (search == null) {
			return -1;
		}
		int result;
		if ((result = context.indexOf(search.getSearchChar(), search.getIndex())) != -1) {
			if (result == 0) {
				return result;
			}
			if (need && context.substring(result - 1, result).equals("\\")) {
				search.setIndex(result + 1);
				return searchPoint(context, search, need);
			}
		}
		return result;
	}

	public Searchs getNeedSearchs(StringAnalysis reverse, int startPoint) {
		Searchs needSearchs = null;
		for (String item : searchs) {
			int tempIndex;
			if ((tempIndex = reverse.indexOf(item, startPoint)) != -1) {
				if (needSearchs == null || needSearchs.getIndex() > tempIndex) {
					needSearchs = new Searchs(tempIndex, item);
				}
			}
		}
		return needSearchs;
	}

	public void deleteNote() {
		int begin = context.indexOf("/*");
		while (begin > -1) {
			StringBuffer reverse = new StringBuffer(context.substring(0, begin)).reverse();
			int line = reverse.indexOf("\n");
			if (line == -1) {
				line = 0;
			}
			reverse = new StringBuffer(reverse.substring(0, line)).reverse();
			if (!isInString(reverse.toString())) {
				int end = context.indexOf("*/", begin);
				context = context.replace(context.substring(begin, end + 2).toString(), "");
			}
			begin = context.indexOf("/*", begin + 2);
		}
		begin = context.indexOf("//");
		while (begin > -1) {
			StringBuffer reverse = new StringBuffer(context.substring(0, begin)).reverse();
			int line = reverse.indexOf("\n");
			if (line == -1) {
				line = 0;
			}
			reverse = new StringBuffer(reverse.substring(0, line)).reverse();
			if (!isInString(reverse.toString())) {
				int end = context.indexOf("\n", begin);
				context = context.replace(context.substring(begin, end).toString(), "");
			}
			begin = context.indexOf("//", begin + 1);
		}
	}

	public void deleteRemark() {
		StringBuffer result = new StringBuffer();
		context.indexOf("/*");

		StringAnalysis.newStringAnalysis(context).split(System.lineSeparator(), new StringHandle() {

			@Override
			public StringAnalysis set(StringAnalysis item) {
				item.replaceAll("//.*", "");
				return item.append("$").append("\n");
			}
		}).printf();

		String[] allLine = context.split(System.lineSeparator());
		for (String line : allLine) {
			int findIndex;
			if ((findIndex = line.indexOf("//")) != -1) {
				String temp = line.substring(0, findIndex);
				if (!temp.contains("\"") && !temp.contains("\'")) {
					line = temp;
				} else {
					temp.replace("\\('|\")+", "");
					int count = StringAnalysis.defaultStringAnalysis(temp).findCount("'");
					count += StringAnalysis.defaultStringAnalysis(null).findCount("\"");
					if (count % 2 == 0) {
						line = temp;
					}
				}
			}
			result.append(line + System.lineSeparator());
		}
		context = result.toString();
	}

	public List<String> getAllContext(String begin, String end) {
		List<String> slist = new ArrayList<>();
		boolean haveBegin = false;
		int beginIndex = 0;
		// int endIndex = 0;
		int indexOf = context.indexOf(begin);
		while (true) {
			if (context.substring(indexOf - 1, indexOf).equals("\\")) {
				indexOf = haveBegin ? context.indexOf(begin, indexOf + 1) : context.indexOf(end, indexOf + 1);
				continue;
			}
			if (haveBegin) {
				// is end
				// endIndex = indexOf+1;
				slist.add(context.substring(beginIndex, indexOf + 1));
				haveBegin = false;
				indexOf = context.indexOf(begin, indexOf + 1);
			} else {
				// is begin
				beginIndex = indexOf;
				// System.out.println(context.substring(endIndex, beginIndex));
				haveBegin = true;
				indexOf = context.indexOf(end, indexOf + 1);
			}
			if (indexOf == -1) {
				if (haveBegin) {
					System.out.println("ERROR");
				}
				break;
			}
		}
		return slist;
	}

	public static String getSource(File path) throws Exception {
		int length = 0;
		byte[] buffer = new byte[512];
		StringBuffer template = new StringBuffer();
		FileInputStream fis = new FileInputStream(path);
		while ((length = fis.read(buffer)) != -1) {
			template.append(new String(buffer, 0, length));
		}
		fis.close();
		return template.toString();
	}

}

class Searchs {

	private Integer index;
	private String searchChar;

	public Searchs() {
	}

	public Searchs(Integer index, String searchChar) {
		this.index = index;
		this.searchChar = searchChar;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getSearchChar() {
		return searchChar;
	}

	public void setSearchChar(String searchChar) {
		this.searchChar = searchChar;
	}

	@Override
	public String toString() {
		return "Searchs [" + index + ":" + searchChar + "]";
	}

}
