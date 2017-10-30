package pers.th;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TimeTest {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd.SSS");
		System.out.println(sdf.format(new Date()));
		execute();
		System.out.println(sdf.format(new Date()));
	}

	private static void execute() {
		Set<BigDecimal> sets = new HashSet<BigDecimal>();
		BigDecimal sum = new BigDecimal(1);
		for (int i = 1; i < 12000000; i++, sum = sum.add(new BigDecimal(i))) {
			sets.add(sum);
		}
		BigDecimal x = new BigDecimal(1);
		for (int i = 1; i < 12000000; i+=2, x = x.add(new BigDecimal(i))) {
			if (sets.contains(x)) {
				System.out.println(x);
			}
		}
	}

}
