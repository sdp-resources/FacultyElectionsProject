package fsc.app;

import fsc.request.*;

import java.util.List;
import java.util.Map;

public class RequestFactory {
  public RequestFactory() { }

  public Request createNamedQuery(String name, String queryString) {
    return new CreateNamedQueryRequest(name, queryString);
  }

  public Request viewProfilesList(String query) {
    return new ViewProfilesListRequest(query);
  }

  public Request createProfile(
        String fullname, String username, String contractType, String division
  ) {
    return new CreateProfileRequest(fullname, username, division, contractType);
  }

  public Request viewProfile(String username) {
    return new ViewProfileRequest(username);
  }

  public Request editProfile(String username, Map<String, String> changes) {
    return new EditProfileRequest(username, changes);
  }

  public Request addDivision(String division) {
    return new AddDivisionRequest(division);
  }

  public Request addContractType(String contractType) {
    return new AddContractTypeRequest(contractType);
  }

  Request viewDivisionList() {
    return new ViewDivisionListRequest();
  }

  Request viewContractTypeList() {
    return new ViewContractsRequest();
  }

  public Request viewCommitteeList() {
    return new ViewCommitteeListRequest();
  }

  public Request createCommittee(String name, String description, String voterQueryString) {
    return new CreateCommitteeRequest(name, description, voterQueryString);
  }

  public Request addSeat(Long committeeId, String seatName, String query) {
    return new CreateSeatRequest(committeeId, seatName, query);
  }

  public Request viewQueryList() {
    return new ViewNamedQueryListRequest();
  }

  public Request validateQuery(String string) {
    return new QueryValidationRequest(string);
  }

  public Request createElection(long seatId) {
    return new CreateElectionRequest(seatId);
  }

  public Request addCandidate(Long electionId, String name) {
    return new AddToBallotRequest(electionId, name);
  }

  public Request submitVote(long voterId, String username, List<String> vote) {
    return new SubmitVoteRecordRequest(voterId, username, vote);
  }

  public Request viewVoteRecord(long recordId) {
    return new ViewVoteRecordRequest(recordId);
  }

  public Request viewAllVotes(Long electionId) {
    return new ViewAllVotesRequest(electionId);
  }

  public Request editSeat(long seatId, Map<String, String> changes) {
    return new EditSeatRequest(seatId, changes);
  }

  public Request addVoter(String username, Long electionId) {
    return new AddVoterRequest(electionId, username);
  }

  public Request viewActiveElections() {
    return new ViewActiveElectionsRequest();
  }

  public Request viewAllElections() {
    return new ViewAllElectionsRequest();
  }

  public Request viewElection(Long electionId) {
    return new ViewElectionRequest(electionId);
  }

  public Request addPasswordRecord(String username, String password, String role) {
    return new AddPasswordRecordRequest(username, password, role);
  }

  public Request login(String username, String password) {
    return new LoginRequest(username, password);
  }

  public Request setElectionState(Long electionId, String state) {
    return new EditElectionStateRequest(electionId, state);
  }

  public Request setDTS(long electionId, String username, String status) {
    return new SetDTSRequest(electionId, username, status);
  }

  public Request viewCandidates(long electionId) {
    return new ViewCandidatesRequest(electionId);
  }

  public Request editNamedQuery(String name, String queryString) {
    return new EditNamedQueryRequest(name, queryString);
  }

  public Request editCommittee(long id, Map<String, Object> changes) {
    return new EditCommitteeRequest(id, changes);
  }

  public Request removeCandidate(Long electionid, String username) {
    return new RemoveFromBallotRequest(electionid, username);
  }

  public Request removeVoter(Long electionid, String username) {
    return new RemoveVoterRequest(electionid, username);
  }

  public Request getElectionResults(long electionID) {
    return new GetElectionResultsRequest(electionID);
  }
}