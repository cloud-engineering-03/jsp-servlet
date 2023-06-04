package model.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JToken {
	static String key = "test";

	public static String createA(String name) {
		return JWT.create().withSubject("name")
//				  .withExpiresAt(null)
				  .withClaim("name", name)
				  .sign(Algorithm.HMAC512(key.getBytes()));
	}
	
	public static String createR(String name, String AT) {
		return JWT.create()
				.withSubject(name)
//				.withExpiresAt(null)
				.withClaim("AccessToken", AT)
				.withClaim("name", name)
				.sign(Algorithm.HMAC512(key));
	}
	
}
