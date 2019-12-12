package fsc.entity;

import fsc.service.Authorizer;
import fsc.service.CryptoWrapper;

import java.util.Objects;

public class PasswordRecord {
  private static final String SALT = "MN9RioDV8jiQOR8VbXrf";
  private String username;
  private String password;
  private Authorizer.Role role;

  public PasswordRecord() { }

  public PasswordRecord(String username, String password, Authorizer.Role role) {

    this.username = username;
    this.password = password;
    this.role = role;
  }

  public static PasswordRecord create(
        String username, String unhashedPassword, Authorizer.Role role
  ) {
    return new PasswordRecord(username, hashPassword(username, unhashedPassword), role);
  }

  public static String hashPassword(String username, String password) {
    String saltedString = SALT + username + password;
    return CryptoWrapper.toSha256(saltedString);
  }

  public boolean matchesPassword(String providedPassword) {
    return hashPassword(username, providedPassword).equals(password);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Authorizer.Role getRole() {
    return role;
  }

  public void setRole(Authorizer.Role role) {
    this.role = role;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PasswordRecord that = (PasswordRecord) o;
    return username.equals(that.username) &&
                 password.equals(that.password) &&
                 role == that.role;
  }

  public int hashCode() {
    return Objects.hash(username, password, role);
  }

  public String toString() {
    return "PasswordRecord{" +
                 "username='" + username + '\'' +
                 ", password='" + password + '\'' +
                 ", role=" + role +
                 '}';
  }
}
