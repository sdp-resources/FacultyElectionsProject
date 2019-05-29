package gateway;

import fsc.entity.*;
import fsc.gateway.Gateway;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InMemoryGateway implements Gateway {

  private final List<Profile> profiles = new ArrayList<>();
  private final List<String> contractTypes = new ArrayList<>();
  private final List<String> divisions = new ArrayList<>();
  private final List<Committee> committees = new ArrayList<>();
  private final List<Election> elections = new ArrayList<>();

  public static InMemoryGateway fromJSONFile(String path) {
    try {
      return fromReader(new JSONElectionDataReader(new File(path)));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("file not found: " + path);
    }
  }

  private static InMemoryGateway fromReader(ElectionDataReader dataReader) {
    InMemoryGateway gateway = new InMemoryGateway();
    dataReader.getContractTypes().forEach(gateway::addContractType);
    dataReader.getDivisions().forEach(gateway::addDivision);
    dataReader.getProfiles().forEach(gateway::addProfile);
    dataReader.getCommittees().forEach(gateway::addCommittee);
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

  public boolean hasProfile(String username) {
    for (Profile currProfile : profiles) {
      if (username.equals(currProfile.username)) return true;
    }
    return false;
  }

  public void addContractType(String contractType) {
    contractTypes.add(contractType);
  }

  public List<String> getAvailableContractTypes() {
    return new ArrayList<>(contractTypes);
  }

  public boolean hasContractType(String contract) {
    return contractTypes.contains(contract);
  }

  public void addDivision(String division) {
    divisions.add(division);
  }

  public Boolean hasDivision(String divisionName) {
    return divisions.contains(divisionName);
  }

  public List<String> getAvailableDivisions() {
    return new ArrayList<>(divisions);
  }

  public Ballot getBallot(String id) {
    return null;
  }

  public void addBallot(Ballot ballot) {}

  public void addElection(Election election) {
    elections.add(election);
  }

  public Committee getCommittee(String committeeName) throws UnknownCommitteeException {
    for (Committee committee : committees) {
      if (committee.getName().equals(committeeName)) {
        return committee;
      }
    }
    throw new UnknownCommitteeException();
  }

  public void addCommittee(Committee committee) {
    committees.add(committee);
  }

  public boolean hasCommittee(String name) {
    for (Committee committee : committees) {
      if (committee.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public void recordVote(VoteRecord voteRecord) {}

  public Election getElection(String electionID) {
    return null;
  }

  public void save() {}

  public List<Committee> getAllCommittees() {
    return new ArrayList<>(committees);
  }

  public Collection<Election> getAllElections() {
    return new ArrayList<>(elections);
  }
}