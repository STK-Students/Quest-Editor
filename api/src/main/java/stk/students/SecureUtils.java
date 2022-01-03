package stk.students;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

//https://coderedirect.com/questions/261022/what-does-0xff-do-and-md5-structure
public class SecureUtils {
    public static String hashPassword(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] bytesPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytesPassword.length; i++) {
                String s = Integer.toString((bytesPassword[i] & 0xff) + 0x100, 16);
                System.out.println((bytesPassword[i] & 0xff) + 0x100);
                stringBuilder.append(s.substring(1));
            }
            generatedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
