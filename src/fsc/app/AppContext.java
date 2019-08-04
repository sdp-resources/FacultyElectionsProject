package fsc.app;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.gateway.Gateway;
import fsc.interactor.*;
import fsc.request.Request;
import fsc.response.Response;
import fsc.service.Authenticator;
import fsc.service.SQLAuthenticator;

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
    Authenticator authenticator = new SQLAuthenticator(gateway);
    return new AuthenticatingInteractor(gateway)
                 .append(new AuthorizingInteractor(gateway))
                 .append(new LoginInteractor(gateway, authenticator, gateway))
                 .append(new DivisionInteractor(gateway, getEntityFactory()))
                 .append(new ContractTypeInteractor(gateway, getEntityFactory()))
                 .append(new ProfileInteractor(gateway, getEntityFactory(), gateway.getNameValidator()))
                 .append(new CommitteeInteractor(gateway, gateway, getEntityFactory(), gateway.getNameValidator()))
                 .append(new QueryInteractor(gateway))
                 .append(new ElectionInteractor(gateway, gateway, gateway, getEntityFactory()));
  }

  public Response getResponse(Request request) {
    try {
      gateway.begin();
      Response response = interactor.handle(request);
      if (!response.isSuccessful()) {
        gateway.rollback();
      } else {
        gateway.commit();
      }
      return response;
    } catch (Exception e) {
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

  public Response getProfile(String username) {
    return getResponse(requestFactory.viewProfile(username));
  }

  public Response getProfilesMatchingQuery(String query) {
    return getResponse(requestFactory.viewProfilesList(query));
  }

  public Response editProfile(String username, Map<String, String> changes) {
    return getResponse(requestFactory.editProfile(username, changes));
  }

  public Response getAllContractTypes(String token) {
    return getResponse(withToken(token, requestFactory.viewContractTypeList()));
  }

  public Response addContractType(String contractType, String token) {
    return getResponse(withToken(token, requestFactory.addContractType(contractType)));
  }

  public Response getAllDivisions() {
    return getResponse(requestFactory.viewDivisionList());
  }

  public Response addDivision(String division) {
    return getResponse(requestFactory.addDivision(division));
  }

  public Response addNamedQuery(String name, String queryString) {
    return getResponse(requestFactory.createNamedQuery(name, queryString));
  }

  public Response getAllQueries() {
    return getResponse(requestFactory.viewQueryList());
  }

  public Response validateQueryString(String string) {
    return getResponse(requestFactory.validateQuery(string));
  }

  public Response getAllCommittees() {
    return getResponse(requestFactory.viewCommitteeList());
  }

  public Response addCommittee(String name, String description, String voterQueryString) {
    return getResponse(
          requestFactory.createCommittee(name, description, voterQueryString));
  }

  public Response addCandidate(Long electionId, String name) {
    return getResponse(requestFactory.addCandidate(electionId, name));
  }

  public Response addSeat(String committeeName, String seatName, String query) {
    return getResponse(requestFactory.addSeat(committeeName, seatName, query));
  }

  public Response editSeat(
        String committeeName, String seatName, Map<String, String> changes
  ) {
    return getResponse(requestFactory.editSeat(committeeName, seatName, changes));
  }

  public Response getAllElections() {
    return getResponse(requestFactory.viewAllElections());
  }

  public Response createElection(String committeeName, String seatName) {
    return getResponse(requestFactory.createElection(committeeName, seatName));
  }

  public Response setElectionState(Long electionId, String state) {
    return getResponse(requestFactory.setElectionState(electionId, state));
  }

  public Response viewElection(Long electionId) {
    return getResponse(requestFactory.viewElection(electionId));
  }

  public Response addVoter(String username, Long electionId) {
    return getResponse(requestFactory.addVoter(username, electionId));
  }

  public Response getAllVotes(Long electionId) {
    return getResponse(requestFactory.viewAllVotes(electionId));
  }

  public Response submitVote(long voterId, List<String> vote) {
    return getResponse(requestFactory.submitVote(voterId, vote));
  }

  public Response getVoteRecord(long recordId) {
    return getResponse(requestFactory.viewVoteRecord(recordId));
  }

  public Response addPasswordRecord(String username, String password, String role) {
    return getResponse(requestFactory.addPasswordRecord(username, password, role));
  }

  public void shutdown() {
    gateway.shutdown();
  }

  public Response login(String username, String password) {
    return getResponse(requestFactory.login(username, password));
  }
}