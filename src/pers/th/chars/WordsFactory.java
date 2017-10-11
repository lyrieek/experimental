package pers.th.chars;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordsFactory {
	
	private static WordsFactory factory;
	
	private static Map<String, Words> maps = new LinkedHashMap<>();

	public synchronized static WordsFactory newFactory() {
		return factory == null ? (factory = new WordsFactory()) : factory;
	}

	public synchronized Words save(String key,Object... param) {
		Words w = builder(param);
		maps.put(key, w);
		return w;
	}
	
	public synchronized Words get(String key) {
		return maps.get(key);
	}

	public synchronized void clear() {
		maps.clear();
	}
	
	
	
	/**
	 * 
	 * @param param
	 * StringAppender to getValue()
	 * List to 
	 * @return
	 */
	public static Words builder(Object... param) {
		Words w = new Words("");
		if (param == null || param.length == 0) {
			return w;
		}
		for (Object obj : param) {
			if (obj instanceof StringAppender) {
				w.append(((StringAppender) obj).getValue());
				continue;
			}
			if (obj instanceof List<?>) {
				List<?> list = (List<?>) obj;
				for (Object item : list) {
					w.append(builder(item).toString());
				}
				continue;
			}
			if (obj instanceof Words) {
				w.append(obj.toString());
				continue;
			}
			w.append(obj == null ? "" : obj.toString());
		}
		return w;
	}

}
