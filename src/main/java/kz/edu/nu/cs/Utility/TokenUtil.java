package kz.edu.nu.cs.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class TokenUtil {
    private KeyGenerator keyGenerator;
    private SecretKey sk;
//    private Key pk;

    public TokenUtil() {
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sk = keyGenerator.generateKey();
//        pk = keyGenerator.generateKey();
    }

    public String isValidToken(String token) {
        keyGenerator = getKeyGenerator();
        String res;
        Key key = getKey();
        try {
            //Decoder jwt = ;
            res = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            System.out.println(res + " reeeeeeeeeeeeeeeees");
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        System.out.println("#### valid token : " + token);
        return res;
    }

    private KeyGenerator getKeyGenerator() {
//        if (keyGenerator == null)
//            try {
//                keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        return keyGenerator;
    }

    public String issueToken(String email) {
        keyGenerator = getKeyGenerator();
        SecretKey key = getKey();
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date afterAddingTenMins = new Date(t + (10 * 60000));
        return Jwts.builder()
                .setSubject(email)
                .setIssuer("baktybek")
                .setIssuedAt(new Date())
                .setExpiration(afterAddingTenMins)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getKey() {
//        if (sk == null)
//            sk = keyGenerator.generateKey();
        return sk;
    }

//    public Key getPkey() {
//        if (pk == null)
//            pk = keyGenerator.generateKey();
//        return pk;
//    }
}
