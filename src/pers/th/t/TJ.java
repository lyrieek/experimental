package pers.th.t;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class TJ {

	public static void main(String[] args) throws Exception {
		String workspace = "./temp/";
		String sourceFile = "./temp/com/temp.java";
		String execFile = "temp/com";
		ToolProvider.getSystemJavaCompiler().run(System.in, System.out, System.err, "-d", workspace, sourceFile);
		URLClassLoader loader=new URLClassLoader(new URL[]{new URL("file:/"+new File(execFile).getAbsolutePath())});
		System.out.println(loader.getURLs()[0]);
		Class<?> c = loader.loadClass("temp");
		c.getMethod("main").invoke(c.newInstance());
		loader.close();
	}
	public void compiler() throws Exception {
		String nr = System.lineSeparator();
		String source = "package temp.com; " + nr + " public class  Hello{" + nr
				+ " public static void main (String[] args){" + nr + " System.out.println(\"HelloWorld! 1\");" + nr
				+ " }" + nr + " }";
		File dir = new File(System.getProperty("user.dir") + "/temp");
		if (!dir.exists()) {
			dir.mkdir();
		}
		FileWriter writer = new FileWriter(new File(dir, "Hello.java"));
		writer.write(source);
		writer.flush();
		writer.close();

		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> it = javaFileManager.getJavaFileObjects(new File(dir, "Hello.java"));
		CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", "./temp"), null,
				it);
		task.call();
		javaFileManager.close();
		Runtime run = Runtime.getRuntime();
		Process process = run.exec("java -cp ./temp temp/com/Hello");
		InputStream in = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String info = "";
		while ((info = reader.readLine()) != null) {
			System.out.println(info);
		}
	}

}
