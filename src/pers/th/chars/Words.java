package pers.th.chars;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Words extends WordBase {
	
	public Words() {
		chars = new StringBuffer();
	}
	
	public Words(String source) {
		chars = new StringBuffer(source);
	}

//	public static void main(String[] args) throws Exception {
//		InputStream reader = new URL("http://192.168.2.47:9000/app/updateI18nData/updateI18nData.controller.js").openStream();
//	}

	public List<Words> getAllBody(String begin, String end) {
		List<Words> slist = new ArrayList<>();
		if (!contains(begin) || !contains(end)) {
			return slist;
		}
		int beginIndex = 0;
		int indexOf = indexOf(begin);
		while (indexOf != -1) {
			beginIndex = indexOf;
			if ((indexOf = indexOf(end, indexOf + 1)) != -1) {
				slist.add(sub(beginIndex + 1, indexOf));
				indexOf = indexOf(begin, indexOf + 1);
			}
		}
		return slist;
	}

	/**
	 * update this value
	 * 
	 * @param param
	 * @return
	 */
	public Words change(Words param) {
		chars = param.chars;
		return this;
	}

	public Words change(String param) {
		return change(new StringBuffer(param));
	}


	public Words change(StringBuffer param) {
		chars = param;
		return this;
	}

	@Override
	public Words clear() {
		super.clear();
		return this;
	}


	/**
	 * get a new Words
	 * 
	 * @param param
	 * @return
	 */
	public Words resolver(Object param) {
		return new Words(param.toString());
	}

	/**
	 * list to [1,2,3]
	 * 
	 * @param param
	 * @return
	 */
	public Words resolver(List<?> param) {
		final List<?> analysisArray = param;
		return new Words("").append("[", new StringAppender() {

			@Override
			public Words getValue() {
				Words context = new Words("");
				for (Object item : analysisArray) {
					context.append("," + item);
				}
				context.delete(0);
				return context;
			}
		}, "]");
	}

	public Words[] resolvers(String[] param) {
		Words[] sa = new Words[param.length];
		for (int i = 0; i < sa.length; i++) {
			sa[i] = new Words(param[i]);
		}
		return sa;
	}

	// chuli
	public Words replaceAll(String oldChar, String newChar) {
		return change(chars.toString().replaceAll(oldChar, newChar));
	}

	public Words replace(String oldChar, String newChar) {
		return change(chars.toString().replace(oldChar, newChar));
	}

	public Words append(Object... param) {
		if (param == null || param.length == 0) {
			chars.append("");
			return this;
		}
		for (Object obj : param) {
			if (obj instanceof StringAppender) {
				chars.append(((StringAppender) obj).getValue());
				continue;
			}
			chars.append(obj);
		}
		return this;
	}

	public Words reverse() {
		return change(chars.reverse());
	} 

	public Words delete(int index) {
		if (isEmpty() || chars.length() < index) {
			return this;
		}
		chars.deleteCharAt(index);
		return this;
	}

	public Words pascal() {
		return replace(" ", "").eq(0).toUpper().append(sub(1));
	}

	public Words camel() {
		return replace(" ", "").eq(0).toLower().append(sub(1));
	}

	public Words toLower() {
		change(chars.toString().toLowerCase());
		return this;
	}

	public Words toUpper() {
		change(chars.toString().toUpperCase());
		return this;
	}

	public Words getBody(String prefix, String suffix) {
		if (!begin(prefix) || !end(suffix)) {
			return resolver("");
		}
		chars.delete(0, prefix.length());
		chars.delete(chars.length() - suffix.length(), chars.length());
		return this;
	}

	public Words insert(int offset, Object obj) {
		if (offset > chars.length()) {
			offset = chars.length();
		}
		chars.insert(offset, obj);
		return this;
	}

	public Words wrap(String prefix, String suffix) {
		chars.insert(0, prefix);
		chars.append(suffix);
		return this;
	}

	public String[] split(String regex) {
		return chars.toString().split(regex);
	}

	public Words split(String regex, StringHandle sh) {
		Words sa = new Words("");
		Words[] content = resolvers(split(regex));
		for (Words item : content) {
			sa.append(sh.set(item));
		}
		return sa;
	}

	public Words trim() {
		return change(chars.toString().trim());
	}

	public String[] toStringArray() {
		if (getBody("[", "]").isEmpty()) {
			return null;
		}
		return split(",");
	}

	public List<String> toList() {
		String array[] = toStringArray();
		List<String> list = new ArrayList<>();
		for (String item : array) {
			list.add(item);
		}
		return list;
	}

	// format
	public Words format(Object... args) {
		return change(String.format(chars.toString(), args));
	}

	// get
	public StringBuffer getStringBuffer() {
		return chars;
	}

	public int size() {
		return chars.length();
	}

	public char[] getChars() {
		return toString().toCharArray();
	}

	public Words sub(int begin, int end) {
		if (size() < begin || size() < end || begin < 0 || end < 0) {
			return null;
		}
		String result = "";
		try {
			result = chars.substring(begin, end);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("substring error:" + chars + "[" + begin + ":" + end + "]");
		}
		return resolver(result);
	}

	public Words sub(int begin) {
		String result = chars.substring(begin);
		return resolver(result);
	}

	public Words eq(int index) {
		return sub(index, index + 1);
	}

	@Override
	public boolean equals(Object obj) {
		return obj.toString().equals(chars.toString());
	}

	public Words printf() {
		System.out.println(toString());
		return this;
	}

	public List<Words> regex(String regex) {
		List<Words> result = new ArrayList<>();
		Matcher matcher = Pattern.compile(regex).matcher(chars);
		while (matcher.find()) {
			result.add(new Words(matcher.group()));
		}
		return result;
	}
	
	public Words at(int i) {
		return resolver(chars.charAt(i));
	}
	
	public Words each(StringHandle sh) {
		char[] eachs = chars.toString().toCharArray();
		Words result = clear();
		for (int i = 0; i < eachs.length; i++) {
			result.append(sh.set(resolver(eachs[i])));
		}
		return result;
	}

	public static Words random() {
		Words bases = new Words("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		Words result = new Words();
		for (int i = 0; i < 10; i++) {
			int index = (int) (Math.random()*(bases.chars.length()));
			result.chars.append(bases.chars.substring(index, index+1));
		}
		result.each(new StringHandle() {
			
			@Override
			public Words set(Words item) {
				return item.append(1);
			}
			
			
		});
		return result;
	}
	
	
}
