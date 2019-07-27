package fixtures;

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
    return appContext.getProfilesMatchingQuery(query).getValues();
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
    return appContext.getAllCommittees().getValues();
  }

  ViewableProfile getProfile(String username) {
    return appContext.getProfile(username).getValues();
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
    return appContext.getAllQueries().getValues();
  }

  ViewableValidationResult validateString(String string) {
    return appContext.validateQueryString(string).getValues();
  }

  boolean addCommittee(String name, String description, String voterQueryString) {
    return appContext.addCommittee(name, description, voterQueryString).isSuccessful();
  }

  boolean addSeat(String committeeName, String seatName, String query) {
    return appContext.addSeat(committeeName, seatName, query).isSuccessful();
  }

  boolean editSeat(
        String committeeName, String seatName, Map<String, String> changes
  ) {
    return appContext.editSeat(committeeName, seatName, changes).isSuccessful();
  }

  ViewableElection createElection(String committeeName, String seatName) {
    return appContext.createElection(committeeName, seatName).getValues();
  }

  boolean addCandidate(Long electionId, String name) {
    return appContext.addCandidate(electionId, name).isSuccessful();
  }

  ViewableVoter addVoter(String username, Long electionId) {
    return appContext.addVoter(username, electionId).getValues();
  }

  List<ViewableVoteRecord> getAllVotes(Long electionId) {
    return appContext.getAllVotes(electionId).getValues();
  }

  ViewableVoteRecord submitVote(long voterId, List<String> vote) {
    return appContext.submitVote(voterId, vote).getValues();
  }

  ViewableVoteRecord getVoteRecord(long recordId) {
    return appContext.getVoteRecord(recordId).getValues();
  }

  Collection<ViewableElection> getAllElections() {
    return appContext.getAllElections().getValues();
  }

  ViewableElection viewElection(Long electionId) {
    return appContext.viewElection(electionId).getValues();
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

  private class TestableAppContext extends AppContext {
    private Session session = new UnauthenticatedSession();

    TestableAppContext(Gateway gateway) {
      super(gateway);
    }

    public Response getResponse(Request request) {
      MyLogger.info("Sending request: " + request);
      request.setSession(session);
      Response response = super.getResponse(request);
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