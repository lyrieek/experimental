package pers.th.sl;

public class Environments {

	private Environments() {
	}

	private static Environments env;

	public static Environments getEnv() {
		return env == null ? (env = new Environments()) : env;
	}

	private String currentPath;

	public String getCurrentPath() {
		return currentPath;
	}

	public String setCurrentPath(String currentPath) {
		return (this.currentPath = currentPath);
	}

}
