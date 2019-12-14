package fsc.app;

import fsc.entity.Candidate;
import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.gateway.Gateway;
import fsc.interactor.*;
import fsc.request.Request;
import fsc.response.Response;
import fsc.service.*;
import fsc.viewable.*;
import fsc.voting.ElectionRecord;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AppContext {
  private static EntityFactory entityFactory = new SimpleEntityFactory();
  public RequestFactory requestFactory = new RequestFactory();
  public Gateway gateway;
  public Interactor interactor;

  public AppContext(Gateway gateway) {
    this.gateway = gateway;
    this.interactor = loadInteractors(gateway);
  }

  public static EntityFactory getEntityFactory() {
    return entityFactory;
  }

  public Interactor loadInteractors(Gateway gateway) {
    SessionCreator sessionCreator = new SessionCreator();
    Authenticator authenticator = new DelegatingAuthenticator(
          new SQLAuthenticator(gateway, sessionCreator),
          new LDAPAuthenticator(sessionCreator));
    return new AuthenticatingInteractor(gateway)
                 .append(new AuthorizingInteractor(gateway))
                 .append(new LoginInteractor(gateway, authenticator, gateway))
                 .append(new DivisionInteractor(gateway, getEntityFactory()))
                 .append(new ContractTypeInteractor(gateway, getEntityFactory()))
                 .append(new ProfileInteractor(gateway, getEntityFactory(),
                                               gateway.getNameValidator()))
                 .append(new CommitteeInteractor(gateway, gateway, getEntityFactory(),
                                                 gateway.getNameValidator()))
                 .append(new QueryInteractor(gateway))
                 .append(new ElectionInteractor(gateway, gateway, gateway, getEntityFactory()));
  }

  public <T> Response<T> getResponse(Request request) {
    try {
      gateway.begin();
      Response<T> response = interactor.handle(request);
      if (!response.isSuccessful()) {
        gateway.rollback();
      } else {
        gateway.commit();
      }
      return response;
    } catch (Exception e) {
      // TODO: Use finally?
      gateway.rollback();
      throw e;
    }
  }

  private Request withToken(String token, Request request) {
    request.token = token;
    return request;
  }

  public Response addProfile(
        String fullname, String username, String contractType, String division
  ) {
    return getResponse(requestFactory.createProfile(fullname, username,
                                                    contractType, division));
  }

  public Response<ViewableProfile> getProfile(String username, String token) {
    return getResponse(withToken(token, requestFactory.viewProfile(username)));
  }

  public Response<List<ViewableProfile>> getProfilesMatchingQuery(String query, String token) {
    return getResponse(withToken(token, requestFactory.viewProfilesList(query)));
  }

  public Response editProfile(String username, Map<String, String> changes) {
    return getResponse(requestFactory.editProfile(username, changes));
  }

  public Response<Collection<String>> getAllContractTypes(String token) {
    return getResponse(withToken(token, requestFactory.viewContractTypeList()));
  }

  public Response addContractType(String contractType, String token) {
    return getResponse(withToken(token, requestFactory.addContractType(contractType)));
  }

  public Response<Collection<String>> getAllDivisions() {
    return getResponse(requestFactory.viewDivisionList());
  }

  public Response addDivision(String division) {
    return getResponse(requestFactory.addDivision(division));
  }

  public Response addNamedQuery(String name, String queryString) {
    return getResponse(requestFactory.createNamedQuery(name, queryString));
  }

  public Response<Map<String, String>> getAllQueries(String token) {
    return getResponse(withToken(token, requestFactory.viewQueryList()));
  }

  public Response<ViewableValidationResult> validateQueryString(String string) {
    return getResponse(requestFactory.validateQuery(string));
  }

  public Response<List<ViewableCommittee>> getAllCommittees(String token) {
    return getResponse(withToken(token, requestFactory.viewCommitteeList()));
  }

  public Response<ViewableCommittee> addCommittee(
        String name, String description,
        String voterQueryString
  ) {
    return getResponse(requestFactory.createCommittee(name, description, voterQueryString));
  }

  public Response editCommittee(String id, Map<String, Object> changes, String token) {
    return getResponse(withToken(token,
                                 requestFactory.editCommittee(Long.valueOf(id),
                                                              changes)));
  }

  public Response addCandidate(Long electionId, String name, String token) {
    return getResponse(withToken(token, requestFactory.addCandidate(electionId, name)));
  }

  public Response<ViewableSeat> addSeat(
        Long committeeId, String seatName, String query
  ) {
    return getResponse(requestFactory.addSeat(committeeId, seatName, query));
  }

  // TODO this and editCommittee should return the viewable changed entities
  public Response editSeat(long seatId, Map<String, String> changes) {
    return getResponse(requestFactory.editSeat(seatId, changes));
  }

  public Response<Collection<ViewableElection>> getActiveElections(String token) {
    return getResponse(withToken(token, requestFactory.viewActiveElections()));
  }

  public Response<Collection<ViewableElection>> getAllElections(String token) {
    return getResponse(withToken(token, requestFactory.viewAllElections()));
  }

  public Response<ViewableElection> createElection(long seatId, String token) {
    return getResponse(withToken(token, requestFactory.createElection(seatId)));
  }

  public Response setElectionState(Long electionId, String state, String token) {
    return getResponse(withToken(token, requestFactory.setElectionState(electionId, state)));
  }

  public Response<ViewableElection> viewElection(Long electionId, String token) {
    return getResponse(withToken(token, requestFactory.viewElection(electionId)));
  }

  public Response<ViewableVoter> addVoter(
        Long electionId, String username, String token
  ) {
    return getResponse(withToken(token, requestFactory.addVoter(username, electionId)));
  }

  public Response<List<ViewableVoteRecord>> getAllVotes(Long electionId) {
    return getResponse(requestFactory.viewAllVotes(electionId));
  }

  public Response<ViewableVoteRecord> submitVote(
        long voterId, String username, List<String> vote, String token
  ) {
    return getResponse(withToken(token, requestFactory.submitVote(voterId, username, vote)));
  }

  public Response<ViewableVoteRecord> getVoteRecord(long recordId) {
    return getResponse(requestFactory.viewVoteRecord(recordId));
  }

  public Response addPasswordRecord(String username, String password, String role) {
    return getResponse(requestFactory.addPasswordRecord(username, password, role));
  }

  public void shutdown() {
    gateway.shutdown();
  }

  public Response<ViewableSession> login(String username, String password) {
    return getResponse(requestFactory.login(username, password));
  }

  public Response<Candidate> setDTS(long electionId, String username, String status, String token) {
    return getResponse(withToken(token, requestFactory.setDTS(electionId, username, status)));
  }

  public Response<Collection<ViewableProfile>> viewBallot(long electionid, String token) {
    return getResponse(withToken(token, requestFactory.viewCandidates(electionid)));
  }

  public <T> Response<T> editNamedQuery(String token, String name, String queryString) {
    return getResponse(withToken(token, requestFactory.editNamedQuery(name, queryString)));
  }

  public Response removeCandidate(Long electionid, String username, String token) {
    return getResponse(withToken(token, requestFactory.removeCandidate(electionid, username)));
  }

  public Response removeVoter(Long electionid, String username, String token) {
    return getResponse(withToken(token, requestFactory.removeVoter(electionid, username)));
  }

  public Response<ElectionRecord> getElectionResults(long electionID, String token) {
    return getResponse(withToken(token, requestFactory.getElectionResults(electionID)));
  }
}