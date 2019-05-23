package fsc.mock;

import fsc.entity.*;
import fsc.gateway.Gateway;

import java.util.List;

public class DTSGateWayDummy implements Gateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException { return null;}

  public void addBallot(Ballot ballot) throws CannotAddBallotException { }

  public void addCommittee(Committee committee) { }

  public void addContractType(String contract) throws Exception { }

  public List<String> getAvailableContractTypes() {return null;}

  public List<String> getAvailableDivisions() {return null;}

  public void addDivision(String division) {}

  public Boolean hasDivision(String divisionName) {return null; }

  public Committee getCommitteeFromCommitteeName(String committeeName) {return null; }

  public void addElection(Election election) {}

  public void recordVote(VoteRecord voteRecord) {}

  public Profile getProfile(String username) throws InvalidProfileUsernameException {return null; }

  public List<Profile> getAllProfiles() {return null; }

  public void addProfile(Profile profile) {}

  public void save() {}
}
