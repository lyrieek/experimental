package pers.ds;

import java.io.File;

public class DataSource {

	private String url;

	public static void main(String[] args) {

	}

	public DataSource(String url) {
		this.url = url;
	}

	public void clear() {
		File dir = new File(url);
		if (dir.isDirectory()) {
			for (File item : dir.listFiles()) {
				item.delete();
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
