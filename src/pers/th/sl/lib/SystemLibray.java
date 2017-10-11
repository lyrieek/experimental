package pers.th.sl.lib;

import java.util.Map.Entry;

import pers.th.sl.Environments;

import java.util.Map;
import java.util.Set;

public class SystemLibray {

	public void printf(String chars) {
		System.out.println(chars);
	}

	public void ll() {
		System.out.println("System Properties");
		printfEntrySet(System.getProperties().entrySet());
		System.out.println("\nSystem Environment");
		printfEntrySet(System.getenv().entrySet());
	}

	public void cd(String... path) {
		if (path != null) {
			System.out.println("update current path:" + Environments.getEnv().setCurrentPath(path[0]));
		}
	}

	public <T> void printfEntrySet(Set<Map.Entry<T, T>> entrySet) {
		for (Entry<T, T> item : entrySet) {
			System.out.println(item.getKey() + ":" + item.getValue());
		}
	}

}
