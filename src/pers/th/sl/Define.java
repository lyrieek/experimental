package pers.th.sl;


public class Define {
	
//	private static final String path = "g:/th/CmsApplicationTest/src/pers/th/sl/define.properties";
//	public static void main(String[] args) throws Exception {
//		Properties prop = new Properties();
//		prop.load(new FileInputStream(path));
//		Enumeration enums = prop.propertyNames();
//		while (enums.hasMoreElements()) {
//			System.out.println(enums.nextElement());
//		}
//	}
	

	public Define(String code) {
		if (!analysis(code,"fn","enum")) {
			this.type = "default";
		}
	}
	
	public boolean isDefault() {
		return "default".equals(type);
	}
	
	public boolean isFunction() {
		return "fn".equals(type);
	}

	private boolean analysis(String code,String... param) {
		int point;
		if (param == null) {
			return false;
		}
		for (String item : param) {
			if ((point = code.indexOf(item))!=-1) {
				this.type = item;
				this.name = code.substring(point+item.length()+1);
				return true;
			}
		}
		return false;
	}

	private String type;
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "{type:"+type+",name:"+name+"}";
	}

	
}
