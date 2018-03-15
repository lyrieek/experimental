package csdn;

import pers.th.util.text.XStrings;

import java.util.Iterator;

public class TextSearch implements Iterable<String>, Iterator<String> {
	private String source;
	private String search;
	private int index = 0;
	private EachItem eachItem;

	public TextSearch() {
		eachItem = new EachItem() {
			
			@Override
			public String next(int index) {
				return source.substring(index, index + search.length());
			}
		};
	}

	public TextSearch(String source, String search) {
		this();
		this.source = source;
		this.search = search;
	}

	public void eachItem(EachItem eachItem) {
		this.eachItem = eachItem;
	}
	
	public void eachAHref() {
		eachItem = new EachItem() {

			@Override
			public String next(int index) {
				return tear("<a href=\"/", "\"");
			}
		};
	}

	public String tear(String begin,String end) {
		return XStrings.tear(source, begin, end, index + search.length());
	}

	public int index() {
		return index;
	}

	@Override
	public boolean hasNext() {
		return source.indexOf(search, index) != -1;
	}

	@Override
	public String next() {
		index = source.indexOf(search, index);
		String result = eachItem.next(index);
		index += result.length();
		return result;
	}

	@Override
	public Iterator<String> iterator() {
		return this;
	}

}

abstract class EachItem {

	public abstract String next(int index);

}
