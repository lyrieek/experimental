package pers.th.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAnalysis {
	
    private static StringAnalysis defSA;

    public static StringAnalysis newStringAnalysis(String param) {
	if (param == null) {
	    param = "";
	}
	return new StringAnalysis(param);
    }

    public synchronized static StringAnalysis defaultStringAnalysis(String param) {
	return (defSA == null) ? (defSA = newStringAnalysis(param)) : defSA;
    }

    public static StringAnalysis getEmpty() {
	return newStringAnalysis("");
    }

    public StringBuffer chars;

    private StringAnalysis(String param) {
	chars = new StringBuffer(param);
    }

    public StringAnalysis(StringBuffer param) {
	chars = param;
    }

    public static void main(String[] args) {
	StringAnalysis sa = StringAnalysis.defaultStringAnalysis("s[df]dsfdfd[s],as[]");
	List<StringAnalysis> sas = sa.getAllBody("[", "]");
	for (StringAnalysis item : sas) {
	    System.out.println(item);
	}
    }

    public List<StringAnalysis> getAllBody(String begin, String end) {
	List<StringAnalysis> slist = new ArrayList<>();
	boolean haveBegin = false;
	int beginIndex = 0;
	// int endIndex = 0;
	int indexOf = indexOf(begin);
	while (true) {
	    if (sub(indexOf - 1, indexOf).equals("\\")) {
		indexOf = haveBegin ? indexOf(begin, indexOf + 1) : indexOf(end, indexOf + 1);
		continue;
	    }
	    if (haveBegin) {
		// is end
		// endIndex = indexOf+1;
		slist.add(sub(beginIndex, indexOf + 1));
		haveBegin = false;
		indexOf = indexOf(begin, indexOf + 1);
	    } else {
		// is begin
		beginIndex = indexOf;
		// System.out.println(context.substring(endIndex, beginIndex));
		haveBegin = true;
		indexOf = indexOf(end, indexOf + 1);
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

    /**
     * update this value
     * 
     * @param param
     * @return
     */
    public StringAnalysis change(StringAnalysis param) {
	chars = param.chars;
	return this;
    }

    public StringAnalysis change(String param) {
	return change(new StringBuffer(param));
    }

    public StringAnalysis change(StringBuffer param) {
	chars = param;
	return this;
    }

    public StringAnalysis clear() {
	chars.setLength(0);
	return this;
    }

    public void analysis() {

    }

    /**
     * if not is int return -1
     * 
     * @return
     */
    public int toInt() {
	if (!isInt()) {
	    return -1;
	}
	return Integer.parseInt(chars.toString());
    }

    // panduan
    public boolean isEmpty() {
	return chars == null || chars.length() == 0;
    }

    public boolean begin(String prefix) {
	return chars.toString().startsWith(prefix);
    }

    public boolean end(String suffix) {
	return chars.toString().endsWith(suffix);
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

    public boolean contains(String... param) {
	for (String item : param) {
	    if (chars.toString().contains(item)) {
		return true;
	    }
	}
	return false;
    }

    public boolean regex(String regex) {
	Matcher matcher = Pattern.compile(regex).matcher(toString());
	int depth = 0;
	while (matcher.find()) {
	    depth++;
	    if (depth > 204800) {
		System.out.println("depth warning!!!");
	    }
	    // TODO
	}
	return chars.toString().matches(regex);
    }

    // reverse
    public StringAnalysis reverse(StringBuffer param) {
	return new StringAnalysis(param);
    }

    /**
     * get a new StringAnalysis
     * 
     * @param param
     * @return
     */
    public StringAnalysis resolver(String param) {
	return new StringAnalysis(param);
    }

    /**
     * list to [1,2,3]
     * 
     * @param param
     * @return
     */
    public StringAnalysis resolver(List<?> param) {
	StringAnalysis sa = StringAnalysis.getEmpty();
	final List<?> analysisArray = param;
	sa.append("[", new StringAppender() {

	    @Override
	    public StringAnalysis getValue() {
		StringAnalysis context = StringAnalysis.getEmpty();
		for (Object item : analysisArray) {
		    context.append("," + item);
		}
		context.delete(0);
		return context;
	    }
	}, "]");
	return sa;
    }

    public StringAnalysis[] resolvers(String[] param) {
	StringAnalysis[] sa = new StringAnalysis[param.length];
	for (int i = 0; i < sa.length; i++) {
	    sa[i] = StringAnalysis.newStringAnalysis(param[i]);
	}
	return sa;
    }

    // chuli
    public StringAnalysis replaceAll(String oldChar, String newChar) {
	return change(chars.toString().replaceAll(oldChar, newChar));
    }

    public StringAnalysis replace(String oldChar, String newChar) {
	return change(chars.toString().replace(oldChar, newChar));
    }

    public StringAnalysis append(Object... param) {
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

    public StringAnalysis reverse() {
	return change(chars.reverse());
    }

    public StringAnalysis delete(int index) {
	if (isEmpty() || chars.length() < index) {
	    return this;
	}
	chars.deleteCharAt(index);
	return this;
    }

    public StringAnalysis pascal() {
	return change(eq(0).toUpper().append(delete(0)));
    }

    public StringAnalysis camel() {
	return change(eq(0).toLower().append(delete(0)));
    }

    public StringAnalysis toLower() {
	change(chars.toString().toLowerCase());
	return this;
    }

    public StringAnalysis toUpper() {
	change(chars.toString().toUpperCase());
	return this;
    }

    public StringAnalysis getBody(String prefix, String suffix) {
	if (!begin(prefix) || !end(suffix)) {
	    return resolver("");
	}
	chars.delete(0, prefix.length());
	chars.delete(chars.length() - suffix.length(), chars.length());
	return this;
    }

    public StringAnalysis insert(int offset, Object obj) {
	if (offset > chars.length()) {
	    offset = chars.length();
	}
	chars.insert(offset, obj);
	return this;
    }

    public StringAnalysis wrap(String prefix, String suffix) {
	chars.insert(0, prefix);
	chars.append(suffix);
	return this;
    }

    public String[] split(String regex) {
	return chars.toString().split(regex);
    }

    public StringAnalysis split(String regex, StringHandle sh) {
	StringAnalysis sa = StringAnalysis.newStringAnalysis("");
	StringAnalysis[] content = resolvers(split(regex));
	for (StringAnalysis item : content) {
	    sa.append(sh.set(item));
	}
	return sa;
    }

    public StringAnalysis trim() {
	return change(chars.toString().trim());
    }

    public String[] toStringArray() {
	if (getBody("[", "]").isEmpty()) {
	    return null;
	}
	return split(",");
    }

    public List<?> toList() {
	String array[] = toStringArray();
	List<String> list = new ArrayList<>();
	for (String item : array) {
	    list.add(item);
	}
	return list;
    }

    // format
    public void format(Object... args) {
	change(String.format(chars.toString(), args));
    }

    // find
    public int findCount(String param) {
	int begin = 0, indexOf, count = 0;
	while ((indexOf = chars.indexOf(param, begin)) != -1) {
	    count++;
	    begin = indexOf + param.length();
	}
	return count;
    }

    public int indexOf(String content) {
	return chars.indexOf(content);
    }

    public int indexOf(String content, int begin) {
	return chars.indexOf(content, begin);
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

    public StringAnalysis sub(int begin, int end) {
	if (size() < begin) {
	    System.err.println("begin > string length:");
//	    System.err.println("\tstring length:" + size());
//	    System.err.println("\tbegin:" + begin);
//	    System.err.println("\tend:" + end);
//	    printf();
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

    public StringAnalysis sub(int begin) {
	String result = chars.substring(begin);
	return resolver(result);
    }

    public StringAnalysis eq(int index) {
	return sub(index, index + 1);
    }

    @Override
    public String toString() {
	return chars.toString();
    }

    @Override
    public boolean equals(Object obj) {
	return obj.toString().equals(chars.toString());
    }

    public StringAnalysis printf() {
	System.out.println(toString());
	return this;
    }

}

interface StringAppender {

    StringAnalysis getValue();

}

interface StringHandle {

    StringAnalysis set(StringAnalysis item);

}

