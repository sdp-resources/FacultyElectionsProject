package fsc.mock;

import fsc.entity.*;
import fsc.entity.query.TrueQuery;
import fsc.gateway.Gateway;

import java.util.ArrayList;
import java.util.List;

public class ViewDTSGatewayDummy implements Gateway {
  public Profile profile = new Profile("skiadas", "skiadas21", "science", "tenured");
  public TrueQuery query = new TrueQuery();
  public Seat seat = new Seat("bigSeat", query);
  public Committee committee = new Committee("coolCommittee", "This committee is really cool.");
  public Ballot ballot = new Ballot();
  public Election election = new Election(seat, committee, query, ballot);
  public Candidate candidate = new Candidate(profile);

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

  public Committee getCommittee(String committeeName) {
    return null;
  }

  public void addElection(Election election) {}

  public void recordVote(VoteRecord voteRecord) {}

  public Election getElection(String electionID) {
    return null;
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    if (profile.username.equals(username)) {
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

  public boolean hasProfile(String username) {
    return false;
  }

  public void save() {

  }

  public boolean hasContractType(String contract) {
    return false;
  }

  public boolean hasCommittee(String name) {
    return false;
  }
}
