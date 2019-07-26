package fsc.service;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Base64;

public class CryptoWrapper {
  private static final SecureRandom secureRandom = new SecureRandom();
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

  public CryptoWrapper() { }

  public static String generateToken(int length) {
    byte[] randomBytes = new byte[length];
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }

  public static String toSha256(String string) {
    return DigestUtils.sha256Hex(string);
  }
}