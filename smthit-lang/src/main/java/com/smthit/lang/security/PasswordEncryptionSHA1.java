/**
 * 
 */
package com.smthit.lang.security;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author Bean
 *
 */
public class PasswordEncryptionSHA1 {
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	/**
	 * 盐的长度
	 */
	public static final int SALT_BYTE_SIZE = 8 / 2;

	/**
	 * 生成密文的长度
	 */
	public static final int HASH_BIT_SIZE = 40 * 4;

	/**
	 * 迭代次数
	 */
	public static final int PBKDF2_ITERATIONS = 1000;

	/**
	 * 
	 */
	public PasswordEncryptionSHA1() {
	}

	public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 用相同的盐值对用户输入的密码进行加密
		String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
		// 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
		return encryptedAttemptedPassword.equals(encryptedPassword);
	}

	public static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
		SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		return toHex(f.generateSecret(spec).getEncoded());
	}

	public static String generateSalt() throws NoSuchAlgorithmException {  
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");  
        byte[] salt = new byte[SALT_BYTE_SIZE];  
        random.nextBytes(salt);  
  
        return toHex(salt);  
    }  
	
	private static byte[] fromHex(String hex) {  
		/**
        byte[] binary = new byte[hex.length() / 2];  
        for (int i = 0; i < binary.length; i++) {  
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);  
        }  
        return binary;  
        */
		return hex.getBytes();
    }  
	
	private static String toHex(byte[] array) {  
        BigInteger bi = new BigInteger(1, array);  
        String hex = bi.toString(16);  
        int paddingLength = (array.length * 2) - hex.length();  
        if (paddingLength > 0)  
            return String.format("%0" + paddingLength + "d", 0) + hex;  
        else  
            return hex;  
    }  
	
	//pbkdf2:sha1:1000$jn97Mfdq$0fa29e3115c7a46da523588b53ec76bee2009631
	public static void main(String[] args) {  
	    String password = "lb11091208";  
	    String salt = "jn97Mfdq";  
	    String ciphertext;  
	    try {  
	  
	        //salt = PasswordEncryption.generateSalt();  
	        ciphertext = PasswordEncryptionSHA1.getEncryptedPassword(password, salt);  
	        //boolean result = PasswordEncryption.authenticate(password, ciphertext, salt);  
	  
	        System.out.println(password + "  " + password.length());  
	        System.out.println(salt + "  " + salt.length());  
	        System.out.println(ciphertext + "  " + ciphertext.length());  
	        //if (result) {  
	         //   System.out.println("succeed");  
	        //} else {  
	         //   System.out.println("failed");  
	        //}  
	    } catch (NoSuchAlgorithmException e) {  
	        System.out.println("NoSuchAlgorithmException");  
	    } catch (InvalidKeySpecException e) {  
	        System.out.println("InvalidKeySpecException");  
	    }  
	}  
}
