package fixtures;

import dbGateway.RedisStore;
import fsc.app.AppContext;
import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;
import fsc.gateway.Gateway;
import fsc.request.Request;
import fsc.response.Response;
import fsc.viewable.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

class AppContextWrapper {
  private final TestableAppContext appContext;

  AppContextWrapper(Gateway gateway) {
    appContext = new TestableAppContext(gateway);
  }

  void setSession(Session session) {
    appContext.setSession(session);
  }

  boolean addProfile(
        String fullname, String username,
        String contractType, String division
  ) {
    return appContext.addProfile(fullname, username, contractType, division)
                     .isSuccessful();
  }

  List<ViewableProfile> getProfilesForQuery(String query) {
    return appContext.getProfilesMatchingQuery(query, null).getValues();
  }

  boolean editProfile(String username, Map<String, String> changes) {
    return appContext.editProfile(username, changes).isSuccessful();
  }

  Collection<String> getAllContractTypes(String token) {
    return appContext.getAllContractTypes(token).getValues();
  }

  Collection<String> getAllDivisions() {
    return appContext.getAllDivisions().getValues();
  }

  List<ViewableCommittee> getAllCommittees() {
    return appContext.getAllCommittees(null).getValues();
  }

  ViewableProfile getProfile(String username) {
    return appContext.getProfile(username, "").getValues();
  }

  boolean addContractType(String contractType, String token) {
    return appContext.addContractType(contractType, token).isSuccessful();
  }

  boolean hasContractType(String contractType) {
    Collection<String> result = getAllContractTypes(null);
    return result.contains(contractType);
  }

  boolean addDivision(String division) {
    return appContext.addDivision(division).isSuccessful();
  }

  boolean hasDivision(String division) {
    Collection<String> result = getAllDivisions();
    return result.contains(division);
  }

  boolean addNamedQuery(String name, String queryString) {
    return appContext.addNamedQuery(name, queryString).isSuccessful();
  }

  Map<String, String> getAllQueries() {
    return appContext.getAllQueries(null).getValues();
  }

  ViewableValidationResult validateString(String string) {
    return appContext.validateQueryString(string).getValues();
  }

  Long addCommittee(String name, String description, String voterQueryString) {
    return appContext.addCommittee(name, description, voterQueryString).getValues().id;
  }

  Long addSeat(Long committeeId, String seatName, String query) {
    return appContext.addSeat(committeeId, seatName, query).getValues().id;
  }

  boolean editSeat(long seatId, Map<String, String> changes) {
    return appContext.editSeat(seatId, changes).isSuccessful();
  }

  ViewableElection createElection(long seatId, String token) {
    return appContext.createElection(seatId, token).getValues();
  }

  boolean addCandidate(Long electionId, String name) {
    return appContext.addCandidate(electionId, name, null).isSuccessful();
  }

  ViewableVoter addVoter(String username, Long electionId) {
    return appContext.addVoter(electionId, username, null).getValues();
  }

  List<ViewableVoteRecord> getAllVotes(Long electionId) {
    return appContext.getAllVotes(electionId).getValues();
  }

  ViewableVoteRecord submitVote(long voterId, String username, List<String> vote) {
    return appContext.submitVote(voterId, username, vote, null).getValues();
  }

  ViewableVoteRecord getVoteRecord(long recordId) {
    return appContext.getVoteRecord(recordId).getValues();
  }

  Collection<ViewableElection> getAllElections() {
    return appContext.getAllElections(null).getValues();
  }

  ViewableElection viewElection(Long electionId) {
    return appContext.viewElection(electionId, null).getValues();
  }

  void shutdown() {
    appContext.shutdown();
  }

  public boolean addPasswordRecord(String username, String password, String role) {
    return appContext.addPasswordRecord(username, password, role).isSuccessful();
  }

  public String login(String username, String password) {
    ViewableSession session = appContext.login(username, password).getValues();
    return session.token;
  }

  public Response setElectionState(Long electionId, String state) {
    return appContext.setElectionState(electionId, state, null);
  }

  private class TestableAppContext extends AppContext {
    private Session session = new UnauthenticatedSession();

    TestableAppContext(Gateway gateway) {
      super(gateway, new RedisStore("localhost"));
    }

    public <T> Response<T> getResponse(Request request) {
      MyLogger.info("Sending request: " + request);
      request.setSession(session);
      Response<T> response = super.getResponse(request);
      MyLogger.info("Got response: " + response);
      return response;
    }

    public void setSession(Session session) {
      this.session = session;
    }

    public Session getSession() {
      return session;
    }
  }
}