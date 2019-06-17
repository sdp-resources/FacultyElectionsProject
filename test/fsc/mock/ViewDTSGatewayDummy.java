package fsc.mock;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.gateway.Gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewDTSGatewayDummy implements Gateway {
  public Profile profile = new Profile("skiadas", "skiadas21", "science", "tenured");
  public Query query = Query.always();
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

  public List<Committee> getCommittees() {
    return null;
  }

  public Committee getCommittee(String committeeName) {
    return null;
  }

  public void addElection(Election election) {}

  public void recordVote(VoteRecord voteRecord) {}

  public boolean hasVoteRecord(Profile voter, Election election) {
    return false;
  }

  public VoteRecord getVoteRecord(Profile voter, Election election) throws NoVoteRecordException {
    return null;
  }

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

  public List<Profile> getProfilesMatching(Query query) {
    return null;
  }

  public void addProfile(Profile profile) {}

  public boolean hasProfile(String username) {
    return false;
  }

  public void addQuery(String name, Query query) {

  }

  public boolean hasQuery(String name) {
    return false;
  }

  public QueryValidationResult validateQueryString(String queryString) {
    return null;
  }

  public void save() {

  }

  public Map<String, Query> getAllQueries() {
    return null;
  }

  public boolean hasContractType(String contract) {
    return false;
  }

  public boolean hasCommittee(String name) {
    return false;
  }
}
