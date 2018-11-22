package kz.edu.nu.cs.Utility;
import java.security.MessageDigest;
public class PasswordHasher {

    public String getPasswordHash(String password) {
        String algorithm = "SHA";
        byte[] plainText = password.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.reset();
            md.update(plainText);
            byte[] encodedPassword = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte anEncodedPassword : encodedPassword) {
                if ((anEncodedPassword & 0xff) < 0x10) {
                    sb.append("0");
                }
                sb.append(Long.toString(anEncodedPassword & 0xff, 16));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
