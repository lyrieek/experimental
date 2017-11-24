package chars.u.s;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * 内容观察器
 * 
 * @author 天浩
 *
 */
public class ContentVisitor implements Iterable<String>, Iterator<String> {

	private int point = 0;
	private int markIndex = -1;
	private String item;
	private String source;
	private boolean needSkip = false;
	private Matcher matcher;
	private Matcher hangMatcher;
	public final static String lineSeparator = "\n";
	
	public ContentVisitor(String sourceText, String searchChar) {
		point = 0;
		this.source = sourceText;
		change(searchChar);
	}

	public static void main(String[] args) throws Exception {
		ContentVisitor cv = new ContentVisitor(IOUtils
				.toString(new FileInputStream("G:/cms/CmsWebApp/client/app/clinic/clinic.controller.spec.js"), "utf-8"),
				".+\n");
		for (String item : cv) {
			if (cv.pureText("a") != -1) {
				System.out.println(item);
			}
		}
	}

	public int pureText(String search) {
		return source.indexOf(search, point);
	}

	/**
	 * 推进
	 */
	public void push(int location, String search) {
		move(location);
		needSkip = true;
		item = search;
	}

	/**
	 * 移动索引
	 * 
	 * @param point
	 */
	public void move(int point) {
		this.point = point;
	}

	@Override
	public boolean hasNext() {
		skip();
		if (!matcher.find(point)) {
			needSkip = false;
			return false;
		}
		push(matcher.start(), matcher.group());
		return true;
	}

	@Override
	public String next() {
		return item();
	}

	public int line() {
		return before().split(lineSeparator).length;
	}

	public int column() {
		int lastLine = before().lastIndexOf(lineSeparator);
		lastLine = (lastLine == -1) ? 0 : lastLine + 1;
		return point - lastLine + 1;
	}

	public String item() {
		return item == null ? "" : item;
	}

	/**
	 * 标记当前位置
	 */
	public void mark() {
		markIndex = point;
	}

	/**
	 * 从记住的位置开始拉取到当前位置
	 */
	public String pull() {
		return source.substring(markIndex, point);
	}

	/**
	 * 更换正则
	 * 
	 * @param regex
	 */
	public void change(String regex) {
		this.matcher = Pattern.compile(regex).matcher(source);
	}

	/**
	 * 挂起当前搜索并更换新搜索
	 */
	public void hang(String newRegex) {
		hangMatcher = matcher;
		change(newRegex);
	}

	/**
	 * 释放被挂起的搜索
	 */
	public void release() {
		matcher = hangMatcher;
	}

	// process

	/**
	 * 跳过已扫描项
	 */
	public void skip() {
		if (needSkip && item != null) {
			needSkip = false;
			this.point += item.length();
		}
	}

	public int location() {
		return point;
	}

	public String before() {
		return source.substring(0, point);
	}

	public String after() {
		return source.substring(point);
	}

	@Override
	public String toString() {
		return point + ":" + item;
	}

	@Override
	public Iterator<String> iterator() {
		return this;
	}

	public String source() {
		return source;
	}

}
