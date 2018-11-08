package kz.edu.nu.cs.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class TokenUtil {
	private KeyGenerator keyGenerator;
	private SecretKey sk;
	private Logger logger;

	public TokenUtil() {
		logger = LoggerFactory.getLogger(TokenUtil.class);
		try {
			keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}
		sk = keyGenerator.generateKey();
	}

	public String isValidToken(String token) {
		keyGenerator = getKeyGenerator();
		String res;
		Key key = getKey();
		try {
			res = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		logger.info("token {} is valid", res);
		return res;
	}

	public String issueToken(String email) {
		keyGenerator = getKeyGenerator();
		SecretKey key = getKey();
		Calendar date = Calendar.getInstance();
		long t = date.getTimeInMillis();
		Date afterAddingTenMins = new Date(t + (10 * 60000));
		String token = Jwts.builder().setSubject(email).setIssuer("baktybek").setIssuedAt(new Date()).setExpiration(afterAddingTenMins).signWith(key, SignatureAlgorithm.HS256).compact();
		logger.info("token issued: {} for {}", token, email);
		return token;
	}

	private KeyGenerator getKeyGenerator() {return keyGenerator;}

	public SecretKey getKey() {return sk;}
}
