package csdn;

import pers.th.util.io.IOUtils;
import pers.th.util.text.XStrings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserManager {

    private static final Set<String> users = new HashSet<>(
            Arrays.asList(IOUtils.reader("src/html/userlist").split(System.lineSeparator())));

    private static final String URLPREFIX = "//my.csdn.net/";

    public int add(String param) {
        if (XStrings.notEmpty(param) && param.matches("[a-zA-Z0-9_-]+")) {
            System.out.println("new [" + param + "]");
            users.add(param);
            return 1;
        }
        return 0;
    }

    public int analysis(String url){
        if (url.startsWith(URLPREFIX)) {
            return add(url.substring(14));
        }
        return 0;
    }

    /**
     * printf all user
     */
    public void printf() {
        for (String item : users) {
            System.out.println(item);
        }
        System.out.println("------------");
        System.out.println("total: " + users.size());
    }

    public static Set<String> users() {
        return users;
    }

    /**
     * 同步到文件
     */
    void sync() {
        writeUser();
    }

    public static void writeUser() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("src/html/userlist"));
            for (String userItem : users) {
                if (XStrings.isBlack(userItem)) {
                    continue;
                }
                writer.write(userItem + System.lineSeparator());
                writer.flush();
            }
            writer.close();
            users.clear();
            users.addAll(Arrays.asList(IOUtils.reader("src/html/userlist").split(System.lineSeparator())));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }
}
