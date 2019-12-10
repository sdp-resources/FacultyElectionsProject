package fsc.service;

import fsc.entity.PasswordRecord;
import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;
import fsc.gateway.PasswordGateway;

import java.time.LocalDateTime;

public class SQLAuthenticator implements Authenticator {

  private static final String SALT = "MN9RioDV8jiQOR8VbXrf";
  public static final int SESSION_DURATION_MINUTES = 30;
  public static final int TOKEN_LENGTH = 24;
  private PasswordGateway gateway;

  public SQLAuthenticator(PasswordGateway gateway) {
    this.gateway = gateway;
  }

  public Session authenticateWithCredentials(Credentials credentials) {
    try {
      PasswordRecord passwordRecord = gateway.getPasswordRecordFor(credentials.getUsername());
      String hashedProvidedPassword = hashPassword(credentials);
      if (! hashedProvidedPassword.equals(passwordRecord.getPassword())) {
        return new UnauthenticatedSession();
      }
      return new AuthenticatedSession(passwordRecord.getRole(),
                                      passwordRecord.getUsername(),
                                      CryptoWrapper.generateToken(TOKEN_LENGTH),
                                      generateExpiryDate());
    } catch (PasswordGateway.UnknownUsernameException e) {
      return new UnauthenticatedSession();
    }
  }

  public PasswordRecord createPasswordRecord(
        String username, String password, Authorizer.Role role
  ) {
    Credentials credentials = new Credentials(username, password);
    return new PasswordRecord(username, hashPassword(credentials), role);
  }

  private LocalDateTime generateExpiryDate() {
    return LocalDateTime.now().plusMinutes(SESSION_DURATION_MINUTES);
  }

  public static String hashPassword(Credentials credentials) {
    String saltedString = SALT + credentials.getUsername() + credentials.getPassword();
    return CryptoWrapper.toSha256(saltedString);
  }

}
