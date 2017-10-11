package pers.th.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityCrypto {

    /**
     * MAC算法可选以下多种算法 HmacMD5,HmacSHA1,HmacSHA256,HmacSHA384,HmacSHA512
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * DES 算法 <br>
     * 可替换为以下任意一种算法，同时key值的size相应改变。 DES,DESede,AES,Blowfish,RC2,RC4
     */
    public static final String ALGORITHM = "DES";

    /**
     * Irreversible encryption 不可逆加密 mode is MD5/DES
     */
    public static String encryption(String mode, byte[] data) {
	MessageDigest sha;
	try {
	    sha = MessageDigest.getInstance(mode);
	    sha.update(data);
	    return new BigInteger(sha.digest()).toString(16);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * DES 算法转换密钥
     */
    private static Key toKey(byte[] key) throws Exception {
	SecretKey secretKey = null;
	if (ALGORITHM.equals("DES") || ALGORITHM.equals("DESede")) {
	    DESKeySpec dks = new DESKeySpec(key);
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
	    secretKey = keyFactory.generateSecret(dks);
	} else {
	    secretKey = new SecretKeySpec(key, ALGORITHM);
	}
	return secretKey;
    }

    /**
     * DES 算法解密
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
	Key k = toKey(decryptBASE64(key));
	Cipher cipher = Cipher.getInstance(ALGORITHM);
	cipher.init(Cipher.DECRYPT_MODE, k);
	return cipher.doFinal(data);
    }

    /**
     * DES 算法加密
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
	Key k = toKey(decryptBASE64(key));
	Cipher cipher = Cipher.getInstance(ALGORITHM);
	cipher.init(Cipher.ENCRYPT_MODE, k);
//	return new BigInteger(cipher.doFinal(data)).toString(16);
	return cipher.doFinal(data);
    }

    /**
     * DES 算法生成密钥
     */
    public static String initKey(String seed) throws Exception {
	SecureRandom secureRandom = null;
	if (seed != null) {
	    secureRandom = new SecureRandom(decryptBASE64(seed));
	} else {
	    secureRandom = new SecureRandom();
	}
	KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
	kg.init(secureRandom);
	SecretKey secretKey = kg.generateKey();
	return encryptBASE64(secretKey.getEncoded());
    }

    public static byte[] decryptBASE64(String key) throws Exception {
	return Base64.decodeBase64(key).getBytes();
    }

    public static String encryptBASE64(byte[] key) throws Exception {
	return Base64.encodeBase64(new String(key));
    }

    /**
     * 初始化HMAC密钥
     * 
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
	KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
	SecretKey secretKey = keyGenerator.generateKey();
	return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC 加密 算法公式 ： HMAC（K，M）=H（K⊕opad∣H（K⊕ipad∣M））,H 代表所采用的HASH算法(如SHA-256),K
     * 代表认证密码 Ko 代表HASH算法的密文 M 代表一个消息输入 B 代表H中所处理的块大小，这个大小是处理块大小，而不是输出hash的大小
     * 如，SHA-1和SHA-256,B = 64 SHA-384和SHA-512 B = 128, L 表示hash的大小 Opad
     * 用0x5c重复B次,Ipad 用0x36重复B次,Apad 用0x878FE1F3重复(L/4)次
     * 
     * HMAC运算步骤
     * <p>
     * First-Hash = H(Ko XOR Ipad || (data to auth))
     * <p>
     * Second-Hash = H(KoXOR Opad || First-Hash)
     */
    public static String encryptHMAC(byte[] data, String key) throws Exception {
	SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
	Mac mac = Mac.getInstance(secretKey.getAlgorithm());
	mac.init(secretKey);
	return new BigInteger(mac.doFinal(data)).toString(16);
    }

    public static void main(String[] args) {
	try {
	    String text = "miwen";
	    System.out.println("MD5加密后:" + encryption("MD5",text.getBytes()));
	    System.out.println("SHA加密后:" + encryption("SHA",text.getBytes()));
	    
	    String base64;
	    System.out.println("BASE64加密后:" + (base64 = SecurityCrypto.encryptBASE64(text.getBytes())));
	    System.out.println("BASE64解密后:" + new String(SecurityCrypto.decryptBASE64(base64)));
	    
	    String macKey = initMacKey();
	    System.out.println("HMAC密匙:" + macKey);
	    System.out.println("HMAC加密后:" + encryptHMAC(text.getBytes(), macKey));
	    
	    String key = initKey(null);
	    byte[] context = encrypt(text.getBytes(), key);
	    System.out.println(ALGORITHM + "密钥:" + key);
	    System.out.println(ALGORITHM + "加密后:" + new BigInteger(context).toString(16));
	    System.out.println(ALGORITHM + "解密后:" +new String(decrypt(context, key)));
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
