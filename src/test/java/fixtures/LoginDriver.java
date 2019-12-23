package fixtures;

public class LoginDriver {
  private final String username;
  private final String password;

  public LoginDriver(String username, String password) {

    this.username = username;
    this.password = password;
  }

  public String logIn() {
    return TestContext.app.login(username, password);
  }

  public boolean makeRequestWithToken(String token) {
    try {
      return TestContext.app.addContractType("aType" + token, token);
    } catch (Exception e) {
      return false;
    }
  }
}

