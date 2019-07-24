package fsc.app;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.gateway.Gateway;
import fsc.interactor.*;
import fsc.request.Request;
import fsc.response.Response;
import fsc.service.query.GatewayBackedQueryValidator;
import fsc.service.query.QueryStringParser;

import java.util.List;
import java.util.Map;

public class AppContext {
  private static EntityFactory entityFactory = new SimpleEntityFactory();
  public RequestFactory requestFactory = new RequestFactory();
  public Gateway gateway;
  public Interactor interactor;
  private GatewayBackedQueryValidator queryValidator;

  public AppContext(Gateway gateway) {
    this.gateway = gateway;
    this.interactor = loadInteractors(gateway);
    this.queryValidator = new GatewayBackedQueryValidator(gateway);
    // TODO: Make queryValidator a local element of the executor
    QueryStringParser.setNameValidator(queryValidator);

  }

  public static EntityFactory getEntityFactory() {
    return entityFactory;
  }

  public Interactor loadInteractors(Gateway gateway) {
    return new DivisionInteractor(gateway, getEntityFactory())
                 .append(new ContractTypeInteractor(gateway, getEntityFactory()))
                 .append(new ProfileInteractor(gateway, getEntityFactory()))
                 .append(new CommitteeInteractor(gateway, gateway, getEntityFactory()))
                 .append(new QueryInteractor(gateway))
                 .append(new ElectionInteractor(gateway, gateway, gateway, getEntityFactory()));
  }

  public Response getResponse(Request request) {
    try {
      gateway.begin();
      Response response = interactor.handle(request);
      if (!response.isSuccessful()) { gateway.rollback(); }
      gateway.commit();
      return response;
    } catch (Exception e) {
      gateway.rollback();
      throw e;
    }
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

  public Response getAllContractTypes() {
    return getResponse(requestFactory.viewContractTypeList());
  }

  public Response addContractType(String contractType) {
    return getResponse(requestFactory.addContractType(contractType));
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

  public void shutdown() {
    gateway.shutdown();
  }
}