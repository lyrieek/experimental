package pers.th.sl;

import org.apache.commons.lang3.StringEscapeUtils;

public class IParam {

	private Object data;

	private boolean isBasic = false;

	private ParamHandle getDataMothod;

	public IParam(Object data) {
		this.data = data;
		isBasic = false;
		getDataMothod = new ParamHandle() {

			@Override
			public Object get(Object p) {
				return p;
			}
		};
	}

	public IParam(Object data, ParamHandle pHandle) {
		this.data = data;
		isBasic = false;
		getDataMothod = pHandle;
	}

	/**
	 * 解析script 解析基本数据类型
	 */
	public void autoAnalysis() {
		if (!data.getClass().getTypeName().startsWith("java.lang.")) {
			return;
		}
		String sourceData = data.toString();
		if (sourceData.matches("\\d")) {
			getDataMothod = new ParamHandle() {

				@Override
				public Object get(Object p) {
					return Integer.valueOf(p.toString());
				}
			};
		} else if (sourceData.matches("(\"|'){1}.+")
				&& sourceData.substring(0, 1).equals(sourceData.substring(sourceData.length() - 1, sourceData.length()))) {
			setData(StringEscapeUtils.unescapeJava(sourceData.substring(1,sourceData.length()-1)));
		}
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isBasic() {
		return isBasic;
	}

	public void setBasic(boolean isBasic) {
		this.isBasic = isBasic;
	}

	public ParamHandle getDataMothod() {
		return getDataMothod;
	}

	public void setGetDataMothod(ParamHandle getDataMothod) {
		this.getDataMothod = getDataMothod;
	}

	public Object get() {
		return getDataMothod.get(data);
	}

}

interface ParamHandle {

	Object get(Object p);

}