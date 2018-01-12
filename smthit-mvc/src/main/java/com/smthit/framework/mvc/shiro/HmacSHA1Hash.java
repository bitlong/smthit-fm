/**
 * 
 */
package com.smthit.framework.mvc.shiro;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecException;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.crypto.UnknownAlgorithmException;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.util.ByteSource;

/**
 * @author Bean
 *
 */
public class HmacSHA1Hash extends CodecSupport implements Hash, Serializable {
	private static final long serialVersionUID = -7089020433309965519L;
	
	private static final int DEFAULT_ITERATIONS = 1;
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	
	private byte[] bytes;
	private ByteSource salt;
	private int iterations = DEFAULT_ITERATIONS;

	/**
	 * 生成密文的长度
	 */
	public static final int HASH_BIT_SIZE = 40 * 4;

	public HmacSHA1Hash() {
	}

	public HmacSHA1Hash(Object source, Object salt, int hashIterations) throws CodecException, UnknownAlgorithmException {
		iterations = Math.max(1, hashIterations);
		ByteSource saltBytes = null;
		if (salt != null) {
			saltBytes = convertSaltToBytes(salt);
			this.salt = saltBytes;
		}
		
		ByteSource sourceBytes = convertSourceToBytes(source);
		
		byte[] hashedBytes = hash(sourceBytes.getBytes(), saltBytes.getBytes(), hashIterations);
		
		setBytes(hashedBytes);
	}

	public HmacSHA1Hash(Object source, Object salt) throws CodecException, UnknownAlgorithmException {
		this(source, salt, 1);
	}

	public HmacSHA1Hash(Object source) throws CodecException, UnknownAlgorithmException {
		this(source, null, 1);
	}

	private byte[] hash(byte[] token, byte[] salt, int hashIterations) throws UnknownAlgorithmException {
		
		try {
			KeySpec spec = new PBEKeySpec(toChars(token), salt, hashIterations, HASH_BIT_SIZE);
			SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);	
			
			return f.generateSecret(spec).getEncoded();

		} catch(NoSuchAlgorithmException exp) {
			throw new UnknownAlgorithmException(exp.getMessage());
		} catch (InvalidKeySpecException exp) {
			throw new UnknownAlgorithmException(exp.getMessage());
		}
		
	}
	
	private ByteSource convertSourceToBytes(Object source) {
		return toByteSource(source);
	}

	private ByteSource convertSaltToBytes(Object salt2) {
		return toByteSource(salt2);
	}
	
	protected ByteSource toByteSource(Object o) {
		if (o == null) {
			return null;
		}
		
		if ((o instanceof ByteSource)) {
			return (ByteSource)o;
		}
		
		byte[] bytes = toBytes(o);
		return ByteSource.Util.bytes(bytes);
	}
	
	@Override
	public byte[] getBytes() {
		return this.bytes;
	}

	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toHex() {
		return Hex.encodeHexString(getBytes());
	}

	@Override
	public String toBase64() {
		return Base64.encodeToString(getBytes());
	}

	@Override
	public boolean isEmpty() {
		return (bytes == null) || (bytes.length == 0);
	}

	@Override
	public String getAlgorithmName() {
		return PBKDF2_ALGORITHM;
	}

	@Override
	public ByteSource getSalt() {
		return salt;
	}

	@Override
	public int getIterations() {
		return iterations;
	}
	
	public static void main(String[] args) {
	    String password = "lb11091208";  
	    String salt = "jn97Mfdq";  
	    
	    HmacSHA1Hash hash = new HmacSHA1Hash(password, salt, 1000);
	    System.out.println("source:" + password);
	    System.out.println("salt:" + salt);
	    System.out.println("hash: " + hash.toHex());
	    
	    //pbkdf2:sha1:1000$jn97Mfdq$0fa29e3115c7a46da523588b53ec76bee2009631
		return;
	}
}
