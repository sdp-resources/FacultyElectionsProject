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
  static String queryAll() { return join("admin", "query"); }

  public static String queryNamed() {
    return queryNamed(":name");
  }

  public static String queryNamed(String name) {
    return join("admin", "query", name);
  }

  static String user() {
    return join("user");
  }
  static String ballot() {
    return ballot(":electionId");
  }

  static String admin() {
    return join("admin");
  }

  static String adminProfile() {
    return join("admin", "profile");
  }

  static String adminElections() {
    return join("admin", "election");
  }

  static String adminAllCommittees() {
    return join("admin", "committee");
  }

  static  String committee() {
    return committee(":committeeName");
  }

  static String committee(Long committeeId) {
    return committee(String.valueOf(committeeId));
  }

  static String adminElection() {
    return adminElection(":electionId");
  }

  static String adminElection(Long electionID) {
    return adminElection(String.valueOf(electionID));
  }

  static String adminElection(String s) {
    return join("election", s);
  }

  static String committee(String committeeId) {
    return join("admin", "committee", committeeId);
  }

  static String seat(String committeeName, String seatName) {
    return join("admin", "seat", committeeName, seatName);
  }

  static String ballot(String name) { return join("ballot", name ); }

  static String ballot(Long electionID) {
    return join("ballot", String.valueOf(electionID));
  }

  public static String decideToStand() {
    return decideToStand(":electionId");
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

  public static String candidateAdd() {
    return candidateAdd(":electionID");
  }

  public static String candidateAdd(String s) {
    return join("candidate", "add", s);
  }

  public static String candidateAdd(Long electionid) {
    return candidateAdd(String.valueOf(electionid));
  }

  public static String voterAdd() {
    return voterAdd(":electionID");
  }

  public static String voterAdd(String s) {
    return join("voter", "add", s);
  }

  public static String voterAdd(Long electionid) {
    return voterAdd(String.valueOf(electionid));
  }

  public static String candidateDelete() {
    return candidateDelete(":electionID", ":username");
  }

  public static String candidateDelete(String electionId, String username) {
    return join("candidate", "delete", electionId, username);
  }

  public static String candidateDelete(Long electionId, String username) {
    return candidateDelete(String.valueOf(electionId), username);
  }

  public static String voterDelete() {
    return voterDelete(":electionID", ":username");
  }

  public static String voterDelete(String electionId, String username) {
    return join("voter", "add", electionId, username);
  }

  public static String voterDelete(Long electionId, String username) {
    return voterDelete( String.valueOf(electionId), username);
  }
}