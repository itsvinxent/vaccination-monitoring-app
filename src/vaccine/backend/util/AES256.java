package vaccine.backend.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AES256 {
    private static SecretKeySpec secretKeySpec;
    private static byte[] key;

    public static String generateSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        return new String(Base64.getEncoder().encode(salt));
    }

    private static void setKey (String salt) {
        MessageDigest digest = null;
        try {
            key = salt.getBytes(StandardCharsets.UTF_8);
            digest = MessageDigest.getInstance("SHA-1");
            key = digest.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String securePassword(String password, String salt, Boolean encryptMode) {
        try {
            setKey(salt);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            if (encryptMode) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
