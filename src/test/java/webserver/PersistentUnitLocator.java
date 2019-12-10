package webserver;

public class PersistentUnitLocator {
  public static String get() {
    String use_local_db = System.getenv("USE_LOCAL_DB");
    if (use_local_db != null && use_local_db.equals("true")) {
      return "inmemoryH2";
    }
    return "testInDocker";
  }
}