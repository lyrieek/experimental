package pers.search;

import java.util.Iterator;

public class TextSearch implements Iterable<String>,Iterator<String>{

	private Integer index;
	private Integer lastIndex;
	private String searchChar;
	private String sourceText;
	private CharJudge cJudge;

	// constructor
	public TextSearch() {
		lastIndex = 0;
		index = 0;
	}

	public TextSearch(String sourceText) {
		this();
		this.sourceText = sourceText;
	}

	public TextSearch(String sourceText, String searchChar) {
		this();
		this.sourceText = sourceText;
		this.searchChar = searchChar;
	}

	public TextSearch(Integer index, String searchChar) {
		this.index = index;
		this.searchChar = searchChar;
	}

	// function
	
	/**
	 * search前保证hasNext()为true
	 * @return
	 */
	public int search() {
		return setIndex(sourceText.indexOf(searchChar, index) + 1);
	}

	@Override
	public boolean hasNext() {
		return (index < sourceText.length()) && sourceText.indexOf(searchChar, index) != -1;
	}

	@Override
	public String next() {
		String result = sourceText.substring(index, search() - 1);
		if (cJudge != null && cJudge.is(result)) {
			return next();
		}
		return result;
	}
	
	/**
	 * @return is true,continue
	 */
	public void not(CharJudge cj) {
		cJudge = cj;
	}

	/**
	 * 之前
	 * @return
	 */
	public String getBeforeText() {
		return sourceText.substring(0, index - 1);
	}

	/**
	 * 之后
	 * @return
	 */
	public String getAfterText() {
		return sourceText.substring(index);
	}

	// get/set
	public Integer getIndex() {
		return index;
	}

	public Integer setIndex(Integer index) {
		lastIndex = this.index;
		return this.index = index;
	}

	public String getSearchChar() {
		return searchChar;
	}

	public void setSearchChar(String searchChar) {
		this.searchChar = searchChar;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}

	public String getSourceText() {
		return sourceText;
	}
	
	public Integer getLastIndex() {
		return lastIndex;
	}
	
	public void setLastIndex(Integer lastIndex) {
		this.lastIndex = lastIndex;
	}

	@Override
	public String toString() {
		return "Searchs [" + index + ":" + searchChar + "]";
	}

	@Override
	public Iterator<String> iterator() {
		return this;
	}

}
interface CharJudge{
	
	public boolean is(String text);
	
}
