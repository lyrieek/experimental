package pers.th.sl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pers.th.sl.lib.SystemLibray;

public class Command {

	// default not param
	boolean haveParam;

	// execution method
	private Method execMethod;

	// execution instance
	private Class<?> instance;

	// execution params
	private List<IParam> params;

	public Command(boolean flag) {
		haveParam = flag;
		params = new ArrayList<>();
	}

	public Command() {
		this(false);
	}

	public void cancelParam() {
		haveParam = false;
		params = null;
	}

	public void addParams(IParam obj) {
		params.add(obj);
		haveParam = true;
	}

	public void setParams(IParam... params) {
		haveParam = true;
		this.params = Arrays.asList(params);
	}

	/**
	 * ����a.b���ʽ a:class b:method
	 */
	public void analysis(String expression) {
		int point = expression.lastIndexOf(".");
		if (point == -1) {
			for (Class<?> classes : new Class<?>[]{SystemLibray.class,System.class}) {
				instance = classes;//SystemLibray.class;
				if(storage(expression.substring(point + 1, expression.length()))){
					return;
				}
			}
		} else {
			String className = expression.substring(0, point);
			try {
				instance = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("class not find:" + className);
			}
		}
		if (instance == null) {
			throw new RuntimeException("can't get instance:" + expression);
		}
		if(!storage(expression.substring(point + 1, expression.length()))){
			throw new RuntimeException("method not find:" + instance);
		}
	}

	/**
	 * �洢һ������
	 * 
	 * @param method
	 */
	public boolean storage(String method) {
		if (instance == null) {
			throw new RuntimeException("instance not find");
		}
		Method[] methods = instance.getMethods();
		for (Method item : methods) {
			if (!item.isAccessible()) {
				item.setAccessible(true);
			}
			if (!item.getName().equals(method)) {
				continue;
			}
			Parameter[] param = item.getParameters();
			if (haveParam) {
				if (param.length == params.size()) {
					this.execMethod = item;
					return true;
				}
			} else {
				if (param != null && param.length > 0 && !item.isVarArgs()) {
					continue;
				} else {
					this.execMethod = item;
					return true;
				}
			}
		}
		return false;
	}

	public Method getExecMethod() {
		return execMethod;
	}

	public void exection() {
		if (execMethod == null) {
			throw new NullPointerException("exection a null method,not storage?");
		}
		try {
			if (!execMethod.isAccessible()) {
				execMethod.setAccessible(true);
			}
			if (!haveParam) {
				if (execMethod.isVarArgs()) {
					execMethod.invoke(instance.newInstance(), new Object[] { null });
					return;
				}
				execMethod.invoke(instance.newInstance());
				return;
			}
			if (Modifier.isStatic(execMethod.getModifiers())) {
				execMethod.invoke(null, reverseParam());
			} else {
				execMethod.invoke(instance.newInstance(), reverseParam());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Object[] reverseParam() {
		Object[] obj = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			obj[i] = params.get(i).get();
		}
		return obj;
	}

}
