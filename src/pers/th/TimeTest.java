package pers.th;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
    
    public static void main(String[] args) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd.SSS");
	System.out.println(sdf.format(new Date()));
	execute();
	System.out.println(sdf.format(new Date()));
    }

    private static void execute() {
	for (int i = 0; i < 100; i++) {
	    System.out.print(1);
	}
	System.out.println();
    }
    
}
