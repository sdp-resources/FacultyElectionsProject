package fixtures;

public class LoginDriver {
  private final String username;
  private final String password;

  public LoginDriver(String username, String password) {

    this.username = username;
    this.password = password;
  }

  public boolean addPasswordRecord(String username, String password, String role) {
    return TestContext.app.addPasswordRecord(username, password, role);
  }
}

