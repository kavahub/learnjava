package io.github.kavahub.learnjava.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SHA512HashHelperTest {
    private static SecureRandom secureRandom;
  
    @BeforeAll
    public static void setUp() throws Exception {
      secureRandom = new SecureRandom();
    }
  
    @Test
    public void givenSamePasswordAndSalt_whenHashed_checkResultingHashesAreEqual() throws Exception {
  
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
  
      String hash1 = SHA512HashHelper.hash("password", salt);
      String hash2 = SHA512HashHelper.hash("password", salt);
  
      assertEquals(hash1, hash2);
  
    }
  
    @Test
    public void givenSamePasswordAndDifferentSalt_whenHashed_checkResultingHashesNotEqual() throws Exception {
  
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
      String hash1 = SHA512HashHelper.hash("password", salt);
      
      //generate a second salt
      byte[] secondSalt = new byte[16];
      secureRandom.nextBytes(salt);
      String hash2 = SHA512HashHelper.hash("password", secondSalt);
  
      assertNotEquals(hash1, hash2);
  
    }
  
    @Test
    public void givenPredefinedHash_whenCorrectAttemptGiven_checkAuthenticationSucceeds() throws Exception {
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
  
      String originalHash = SHA512HashHelper.hash("password123", salt);

      assertTrue(SHA512HashHelper.checkPassword(originalHash, "password123", salt));
    }
  
    @Test
    public void givenPredefinedHash_whenIncorrectAttemptGiven_checkAuthenticationFails() throws Exception {
      byte[] salt = new byte[16];
      secureRandom.nextBytes(salt);
  
      String originalHash = SHA512HashHelper.hash("password123", salt);
  
      assertFalse(SHA512HashHelper.checkPassword(originalHash, "password124", salt));
    }    
}
