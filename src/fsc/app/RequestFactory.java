package fsc.app;

import fsc.request.*;

import java.util.List;
import java.util.Map;

public class RequestFactory {
  public RequestFactory() { }

  public Request createNamedQuery(String name, String queryString) {
    return new CreateNamedQueryRequest(name, queryString);
  }

  Request viewProfilesList(String query) {
    return new ViewProfilesListRequest(query);
  }

  Request createProfile(
        String fullname, String username, String contractType, String division
  ) {
    return new CreateProfileRequest(fullname, username, division, contractType);
  }

  Request viewProfile(String username) {
    return new ViewProfileRequest(username);
  }

  public Request editProfile(String username, Map<String, String> changes) {
    return new EditProfileRequest(username, changes);
  }

  Request addDivision(String division) {
    return new AddDivisionRequest(division);
  }

  Request addContractType(String contractType) {
    return new AddContractTypeRequest(contractType);
  }

  Request viewDivisionList() {
    return new ViewDivisionRequest();
  }

  Request viewContractTypeList() {
    return new ViewContractsRequest();
  }

  public Request viewCommitteeList() {
    return new ViewCommitteeListRequest();
  }

  public Request createCommittee(String name, String description) {
    return new CreateCommitteeRequest(name, description);
  }

  public Request addSeat(String committeeName, String seatName, String query) {
    return new CreateSeatRequest(committeeName, seatName, query);
  }

  public Request viewQueryList() {
    return new ViewNamedQueryListRequest();
  }

  public Request validateQuery(String string) {
    return new QueryValidationRequest(string);
  }

  public Request createElection(String committeeName, String seatName) {
    return new CreateElectionRequest(seatName, committeeName);
  }

  public Request addCandidate(Long electionId, String name) {
    return new AddToBallotRequest(electionId, name);
  }

  public Request submitVote(long voterId, List<String> vote) {
    return new SubmitVoteRecordRequest(voterId, vote);
  }

  public Request viewVoteRecord(long recordId) {
    return new ViewVoteRecordRequest(recordId);
  }

  public Request viewAllVotes(Long electionId) {
    return new ViewAllVotesRequest(electionId);
  }

  public Request editSeat(String committeeName, String seatName, Map<String, String> changes) {
    return new EditSeatRequest(committeeName, seatName, changes);
  }

  public Request addVoter(String username, Long electionId) {
    return new AddVoterRequest(username, electionId);
  }
}