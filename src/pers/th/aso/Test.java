package pers.th.aso;

import java.util.Random;

import oracle.net.aso.p;
import pers.th.sl.SimpleLanguage;

public class Test {
	public static void main(String[] args) {
		SimpleLanguage sl = new SimpleLanguage();
//		System.out.println(new Scanner(System.in).nextLine().replace("\\", "/"));
		sl.split(pers.th.util.FileReader.reader("G:/th/CmsApplicationTest/src/pers/th/sl/test"));
		sl.pull();
		sl.exec();
//		sl.printGroup();
	}
	
	public static void pByte(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i]);
		}
		System.out.println();
	}

	public static final byte[] a(byte[] paramArrayOfByte) {
		byte[] arrayOfByte1 = new byte[8];
		byte[] arrayOfByte2 = new byte[8];
		Random localRandom;
		(localRandom = new Random()).nextBytes(arrayOfByte1);
		localRandom.nextBytes(arrayOfByte2);
		byte[] arrayOfByte3 = {121, 111, 114, -123, -82, -107, -109, 0};
		byte[] arrayOfByte4 = new byte[8];
		for (int i = 0; i < arrayOfByte1.length; ++i)
			arrayOfByte4[i] = (byte) (arrayOfByte1[i] ^ arrayOfByte2[i] ^ arrayOfByte3[i]);
		p localp = new p();
		byte[] arrayOfByte5 = new byte[8];
		try {
			localp.a(arrayOfByte4, arrayOfByte5);
		} catch (Exception localException) {
		}
		byte[] arrayOfByte6;
		int j = (arrayOfByte6 = localp.a(paramArrayOfByte, paramArrayOfByte.length)).length - 1;
		byte[] arrayOfByte7 = new byte[arrayOfByte1.length + arrayOfByte2.length + j];
		System.arraycopy(arrayOfByte1, 0, arrayOfByte7, 0, arrayOfByte1.length);
		System.arraycopy(arrayOfByte6, 0, arrayOfByte7, arrayOfByte1.length, j / 2);
		System.arraycopy(arrayOfByte2, 0, arrayOfByte7, arrayOfByte1.length + j / 2, arrayOfByte2.length);
		System.arraycopy(arrayOfByte6, j / 2, arrayOfByte7, arrayOfByte1.length + j / 2 + arrayOfByte2.length,
				j - (j / 2));
		j localj;
		(localj = new j()).a(arrayOfByte7, 0, arrayOfByte7.length);
		byte[] arrayOfByte8 = new byte[16];
		localj.a(arrayOfByte8, 0);
		byte[] arrayOfByte9 = new byte[arrayOfByte7.length + arrayOfByte8.length];
		System.arraycopy(arrayOfByte7, 0, arrayOfByte9, 0, arrayOfByte7.length);
		System.arraycopy(arrayOfByte8, 0, arrayOfByte9, arrayOfByte7.length, arrayOfByte8.length);
		return arrayOfByte9;
	}
}
