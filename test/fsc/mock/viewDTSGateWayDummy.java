package fsc.mock;

import fsc.entity.*;
import fsc.gateway.Gateway;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

public class viewDTSGateWayDummy implements Gateway {
  public Profile profile = new Profile("skiadas", "skiadas21","science","tenured");
  public AlwaysTrueQueryStub query = new AlwaysTrueQueryStub();
  public Seat seat = new Seat("bigSeat", query);
  public Committee committee = new Committee("coolCommittee","This committee is really cool.");
  public Ballot ballot = new Ballot();
  public Election election = new Election(seat, committee, query, ballot);



  public Ballot getBallot(String id) throws InvalidBallotIDException {
    ballot.add(profile);
    return ballot;
  }

  public void addBallot(Ballot ballot) throws CannotAddBallotException {

  }

  public void addCommittee(Committee committee) {

  }

  public void addContractType(String contract) throws Exception {

  }

  public List<String> getAvailableContractTypes() {
    return null;
  }

  public List<String> getAvailableDivisions() {
    return null;
  }

  public void addDivision(String division) {

  }

  public Boolean hasDivision(String divisionName) {
    return null;
  }

  public Committee getCommitteeFromCommitteeName(String committeeName) {
    return null;
  }

  public void addElection(Election election) {

  }

  public void recordVote(VoteRecord voteRecord) {

  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    return profile;
  }

  public List<Profile> getAllProfiles() {
    List<Profile> profiles = new ArrayList<Profile>();
    profiles.add(profile);
    return profiles;
  }

  public void addProfile(Profile profile) {

  }

  public void save() {

  }
}
