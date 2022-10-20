package book.example.mordernjavainaction.chap08;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class CacheExample {

    public static byte[] calculateDigest(String key){
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
    }
}
