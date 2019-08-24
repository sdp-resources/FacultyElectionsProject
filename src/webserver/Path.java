package webserver;

import fsc.entity.Election;

public class Path {
  static String root() {
    return join("");
  }

  static String login() {
    return join("login");
  }

  static String logout() {
    return join("logout");
  }

  static String validate() {
    return join("validate");
  }
  static String query() { return join("query"); }

  static String user() {
    return join("user");
  }

  static String ballot() {
    return ballot(":electionid");
  }
  static String admin() {
    return join("admin");
  }

  static String adminProfile() {
    return join("admin", "profile");
  }

  static String adminElection() {
    return join("admin", "election");
  }

  static String adminCommittee() {
    return join("admin", "committee");
  }

  static String ballot(String name) { return join("ballot", name ); }

  static String ballot(Long electionID) {
    return join("ballot", String.valueOf(electionID));
  }

  public static String decideToStand() {
    return decideToStand(":electionid");
  }

  private static String decideToStand(String name) {
    return join("decideToStand", name);
  }

  public static String decideToStand(Long electionID) {
    return decideToStand(String.valueOf(electionID));
  }

  public static String decideToStand(Election election) {
    return decideToStand(election.getID());
  }

  private static String join(String... parts) {
    return "/" + String.join("/", parts);
  }
}