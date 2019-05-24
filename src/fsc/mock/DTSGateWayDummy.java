package fsc.mock;

import fsc.entity.*;
import fsc.gateway.Gateway;

import java.util.List;

public class DTSGateWayDummy implements Gateway {
  public Ballot getBallot(String id) { return null;}

  public void addBallot(Ballot ballot) { }

  public void addCommittee(Committee committee) { }

  public void addContractType(String contract) { }

  public List<String> getAvailableContractTypes() {return null;}

  public List<String> getAvailableDivisions() {return null;}

  public void addDivision(String division) {}

  public Boolean hasDivision(String divisionName) {return null; }

  public Committee getCommitteeFromCommitteeName(String committeeName) {return null; }

  public void addElection(Election election) {}

  public void recordVote(VoteRecord voteRecord) {}

  public Election getElectionFromElectionID(String electionID) {
    return null;
  }

  public Profile getProfile(String username) {return null; }

  public List<Profile> getAllProfiles() {return null; }

  public void addProfile(Profile profile) {}

  public void save() {}
}
