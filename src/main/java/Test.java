import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Test {
	
	static String makeSalt() {
		String salt = null;
		
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			sr.nextBytes(bytes);
			salt = new String(Base64.getEncoder().encode(bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static String sha256(String passwd) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(passwd.getBytes());
			byte[] data = md.digest();
			System.out.println(Arrays.toString(data));
			for(byte b : data) {
				sb.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	public static void main(String[] args) {
//		5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8
		System.out.println(sha256("password"));
		System.out.println(sha256("password").length());
	}

}
