package gateway;

import fsc.entity.*;
import fsc.gateway.Gateway;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGateway implements Gateway {

  public ArrayList<Profile> profiles = new ArrayList<>();
  private List<String> contractTypes = new ArrayList<>();

  public InMemoryGateway() {
    profiles.add(new Profile("Haris", "skiadas", "Math", "tenured"));
    profiles.add(new Profile("Theresa", "wilson", "CS", "tenured"));
  }

  public ArrayList<Profile> getAllProfiles() {
    return profiles;
  }

  public Profile getProfile(String username) {
    for (Profile currProfile : profiles) {
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    throw new RuntimeException("No Profile With that Username");
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  public boolean isValidDivision(String division) {
    return false;
  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    return (currProfile.username.equals(username));

  }

  public void addContractType(String contractType) {
    contractTypes.add(contractType);
  }

  public void checkIfContractTypeExist(String contract_type) throws InvalidContractTypeException {

  }

  public ArrayList<String> getDivisionList() {
    return null;
  }

  public List<String> getAllDivisions() {
    return null;
  }

  public String getDivision(String divisionName) throws Exception {
  return divisionName;
  }

  public void addDivision(String division) {

  }

  public Boolean hasDivision(String divisionName) {
    return true;
  }

  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void addBallot(Ballot ballot) throws CannotAddBallotException {

  }

  public Seat getSeat(String seatName) throws InvalidSeatNameException {
    return null;
  }

  public void addElection(Election makeElectionFromRequest) {

  }

  public VoteRecord getVoteRecord() {
    return null;
  }

  public void save() { }
}
