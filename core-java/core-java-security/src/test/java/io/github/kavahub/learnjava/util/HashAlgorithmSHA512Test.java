package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.HashAlgorithmSHA512.*;

/**
 * 
 * {@link HashAlgorithmSHA512} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HashAlgorithmSHA512Test {
    private static SecureRandom secureRandom;
  
    @BeforeAll
    public static void setUp() throws Exception {
      secureRandom = new SecureRandom();
    }
  
    @Test
    public void givenSamePasswordAndSalt_whenHashed_checkResultingHashesAreEqual() throws Exception {
  
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
  
      String hash1 = hash("password", salt);
      String hash2 = hash("password", salt);
  
      assertEquals(hash1, hash2);
  
    }
  
    @Test
    public void givenSamePasswordAndDifferentSalt_whenHashed_checkResultingHashesNotEqual() throws Exception {
  
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
      String hash1 = hash("password", salt);
      
      //generate a second salt
      byte[] secondSalt = new byte[16];
      secureRandom.nextBytes(salt);
      String hash2 = hash("password", secondSalt);
  
      assertNotEquals(hash1, hash2);
  
    }
  
    @Test
    public void givenPredefinedHash_whenCorrectAttemptGiven_checkAuthenticationSucceeds() throws Exception {
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
  
      String originalHash = hash("password123", salt);

      assertTrue(checkPassword(originalHash, "password123", salt));
    }
  
    @Test
    public void givenPredefinedHash_whenIncorrectAttemptGiven_checkAuthenticationFails() throws Exception {
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
  
      String originalHash = hash("password123", salt);
  
      assertFalse(checkPassword(originalHash, "password124", salt));
    }    
}
