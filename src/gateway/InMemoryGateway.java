package gateway;

import fsc.entity.*;
import fsc.gateway.Gateway;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class InMemoryGateway implements Gateway {

  private List<Profile> profiles = new ArrayList<>();
  private List<String> contractTypes = new ArrayList<>();
  private List<String> divisions = new ArrayList<>();

  public InMemoryGateway() {
//    profiles.add(new Profile("Haris", "skiadas", "Math", "tenured"));
//    profiles.add(new Profile("Theresa", "wilson", "CS", "tenured"));
  }

  public ArrayList<Profile> getAllProfiles() {
    return new ArrayList<>(profiles);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    for (Profile currProfile : profiles) {
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    throw new InvalidProfileUsernameException();
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    return (currProfile.username.equals(username));

  }

  public void addContractType(String contractType) {
    contractTypes.add(contractType);
  }

  public List<String> getContractTypes() {
    return new ArrayList<>(contractTypes);
  }

  public void addDivision(String division) {
    divisions.add(division);
  }

  public String getDivision(String divisionName) throws Exception {
    return null;
  }

  public Boolean hasDivision(String divisionName) {
    return divisions.contains(divisionName);
  }

  public List<String> getAllDivisions() {
    return new ArrayList<>(divisions);
  }

  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void addBallot(Ballot ballot) throws CannotAddBallotException {

  }

  public void addElection(Election makeElectionFromRequest) {

  }

  public Committee getCommitteeFromCommitteeName(String committeeName) {
    return null;
  }

  public void addCommittee(Committee committee) {

  }

  public void recordVote (VoteRecord voteRecord){}

  public void save () { }

  public static InMemoryGateway fromFile(File file) throws FileNotFoundException {
    InMemoryGateway gateway = new InMemoryGateway();
    JSONObject json = readJson(file);
    addContractTypes(gateway, json);
    addDivisions(gateway, json);
    return gateway;
  }

  private static JSONObject readJson(File file) throws FileNotFoundException {
    FileReader reader = new FileReader(file);
    JSONTokener tokener = new JSONTokener(reader);
    return new JSONObject(tokener);
  }

  private static void addContractTypes(InMemoryGateway gateway, JSONObject json) {
    for (Object s : json.getJSONArray("contractTypes")) {
      gateway.addContractType((String) s);
    }
  }

  private static void addDivisions(InMemoryGateway gateway, JSONObject json) {
    for (Object s : json.getJSONArray("divisions")) {
      gateway.addDivision((String) s);
    }
  }


}