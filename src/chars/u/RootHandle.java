package chars.u;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RootHandle {
	
	
	@Handle("read")
	public String read(String item) {
		if (item.equals("=")) {
			return "operator";
		}
		if (item.startsWith("/*")) {
			return "remark";
		}
		return "read";
	}
	
	@Handle("operator")
	public String op(String item) {
		return "read";
	}

	@Handle("remark")
	public String remark(String item) {
		if (item.endsWith("*/")) {
			return "remark|read";
		}
		return "remark";
	}


}

