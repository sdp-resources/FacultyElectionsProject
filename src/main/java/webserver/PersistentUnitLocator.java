package webserver;

public class PersistentUnitLocator {
  public static String get() {
    String fec_env = System.getenv("FEC_ENV");
    if (fec_env == null) {fec_env = "DEV";}
    if (fec_env.toLowerCase().equals("dev")) {
      return "inmemoryH2";
    } else if (fec_env.toLowerCase().equals("prod")) {
        return "testInDocker";
    } else {
      throw new RuntimeException("Unknown environment: " + fec_env);
    }
  }
}