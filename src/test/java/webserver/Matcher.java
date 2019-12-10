package webserver;

public class Matcher {
  static String contains(String text) {
    return ":contains(" + text + ")";
  }

  static String attribute(String tag, String attribute, String value) {
    return tag + "[" + attribute + "=\"" + value + "\"]";
  }
}