package fsc.mock;

import fsc.entity.*;
import fsc.gateway.Gateway;

import java.util.ArrayList;
import java.util.List;

public class ViewDTSGatewayDummy implements Gateway {
  public final Profile profile = new Profile("skiadas", "skiadas21","science","tenured");
  public final AlwaysTrueQueryStub query = new AlwaysTrueQueryStub();
  public final Seat seat = new Seat("bigSeat", query);
  public final Committee committee = new Committee("coolCommittee","This committee is really cool.");
  public final Ballot ballot = new Ballot();
  public final Election election = new Election(seat, committee, query, ballot);
  public final Candidate candidate = new Candidate(profile);

  public Ballot getBallot(String id) {
    ballot.add(profile);
    return ballot;
  }

  public void addBallot(Ballot ballot) {}

  public void addCommittee(Committee committee) {}

  public void addContractType(String contract) {}

  public List<String> getAvailableContractTypes() {
    return null;
  }

  public List<String> getAvailableDivisions() {
    return null;
  }

  public void addDivision(String division) {}

  public Boolean hasDivision(String divisionName) {
    return null;
  }

  public Committee getCommitteeFromCommitteeName(String committeeName) {
    return null;
  }

  public void addElection(Election election) {}

  public void recordVote(VoteRecord voteRecord) {}

  public Election getElectionFromElectionID(String electionID) {
    return null;
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    if (profile.username.equals(username)){
      return profile;
    }
    throw new InvalidProfileUsernameException();
  }

  public List<Profile> getAllProfiles() {
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);
    return profiles;
  }

  public void addProfile(Profile profile) {}

  public void save() {

  }
}
