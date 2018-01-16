package pers.http;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import pers.th.util.io.IOUtils;

public class UserManager {

	private static final Set<String> users = new HashSet<>(
			Arrays.asList(IOUtils.reader("src/html/userlist").split(System.lineSeparator())));

	public static void main(String[] args) {
		for (String item : users) {
			System.out.println(item);
		}
		System.out.println(users.size());
	}
	
	public static Set<String> users() {
		return users;
	}

	public static void writeUser() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File("src/html/userlist"));
			for (String userItem : users) {
				writer.write(userItem + System.lineSeparator());
				writer.flush();
			}
			users.clear();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
