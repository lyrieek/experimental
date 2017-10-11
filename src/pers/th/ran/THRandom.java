package pers.th.ran;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class THRandom {

	private static final long multiplier = 0x5DEECE66DL;
	private static final long addend = 0xBL;
	private static final long mask = (1L << 48) - 1;
	private static long seedValue = 1024;
	private static int bits = 32;

	public static void main(String[] args) {
		System.out.println(new Random(seedValue).nextInt(100));
		System.out.println(nextInt(100));
	}

	public static int nextInt(int bound) {
		bits = 31;
		int r = next();
		int m = bound - 1;
		if ((bound & m) == 0)
			r = (int) ((bound * (long) r) >> 31);
		else {
			for (int u = r; u - (r = u % bound) + m < 0; u = next())
				;
		}
		return r;
	}

	static int next() {
		AtomicLong seed = new AtomicLong((seedValue ^ multiplier) & mask);
		long oldseed, nextseed;
		do {
			oldseed = seed.get();
			nextseed = (oldseed * multiplier + addend) & mask;
		} while (!seed.compareAndSet(oldseed, nextseed));
		return (int) (nextseed >>> (48 - bits));
	}
}
