package gateway;

import fsc.entity.*;
import fsc.gateway.Gateway;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryGateway implements Gateway {

  private List<Profile> profiles = new ArrayList<>();
  private List<String> contractTypes = new ArrayList<>();
  private List<String> divisions = new ArrayList<>();

  public static InMemoryGateway fromJSONFile(File file) throws FileNotFoundException {
    return fromReader(new JSONElectionDataReader(file));
  }

  private static InMemoryGateway fromReader(ElectionDataReader dataReader) {
    InMemoryGateway gateway = new InMemoryGateway();
    dataReader.getContractTypes().forEach(gateway::addContractType);
    dataReader.getDivisions().forEach(gateway::addDivision);
    dataReader.getProfiles().forEach(gateway::addProfile);
    return gateway;
  }

  public ArrayList<Profile> getAllProfiles() {
    return new ArrayList<>(profiles);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    for (Profile currProfile : profiles) {
      if (username.equals(currProfile.username)) return currProfile;
    }
    throw new InvalidProfileUsernameException();
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
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

  public Boolean hasDivision(String divisionName) {
    return divisions.contains(divisionName);
  }

  public List<String> getAllDivisions() {
    return new ArrayList<>(divisions);
  }

  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void addBallot(Ballot ballot) throws CannotAddBallotException {}

  public void addElection(Election makeElectionFromRequest) {}

  public Committee getCommitteeFromCommitteeName(String committeeName) {
    return null;
  }

  public void addCommittee(Committee committee) {}

  public void recordVote(VoteRecord voteRecord) {}

  public void save() {}

}