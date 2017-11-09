package chars.u;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Function {

	private String name;
	private Method method;
	private Object instance;
	private Object[] args;

	public Function(Method param) {
		method = param;
	}

	public Function(Method param, String name) {
		method = param;
		this.name = name;
	}

	public Function(Method param, String name, Object instance) {
		method = param;
		this.name = name;
		this.instance = instance;
	}

	public void init() {

	}

	public Class<?>[] getNeedParam() {
		return method.getParameterTypes();
	}

	public boolean hasNeedParam(Class<?> param) {
		for (Class<?> classes : getNeedParam()) {
			if (classes.equals(param)) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public boolean equalsName(String name) {
		return this.name.equals(name);
	}

	public Method getMethod() {
		return method;
	}

	public Object exec() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (args != null) {
			return exec(instance, args);
		}
		return method.invoke(instance);
	}

	public Object exec(Object... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(instance, this.args = args);
	}

	public void saveParam(Object... objects) {
		this.args = objects;
	}

	public Object getInstance() {
		return instance;
	}

	@Override
	public String toString() {
		return method.toString();
	}

}
