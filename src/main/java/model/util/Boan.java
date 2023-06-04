package model.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Boan {
	
	public static String salt() {
		String salt = null;
		
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			sr.nextBytes(bytes);
			salt = new String(Base64.getEncoder().encode(bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return salt;
	}
	
	public static String change(String passwd) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(passwd.getBytes());
			byte[] data = md.digest();
//			System.out.println(Arrays.toString(data));
			for(byte b : data) {
				sb.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
