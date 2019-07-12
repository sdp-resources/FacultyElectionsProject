package fsc.app;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.gateway.Gateway;
import fsc.interactor.*;
import fsc.request.Request;
import fsc.response.Response;
import fsc.service.query.GatewayBackedQueryValidator;
import fsc.service.query.QueryStringParser;
import fsc.viewable.*;

import java.util.List;
import java.util.Map;

public class AppContext {
  private static EntityFactory entityFactory = new SimpleEntityFactory();
  private RequestFactory requestFactory = new RequestFactory();
  public Gateway gateway;
  public Interactor interactor;
  private GatewayBackedQueryValidator queryValidator;

  public AppContext(Gateway gateway) {
    this.gateway = gateway;
    this.interactor = loadInteractors(gateway);
    this.queryValidator = new GatewayBackedQueryValidator(gateway);
    QueryStringParser.setNameValidator(queryValidator);
  }

  public static EntityFactory getEntityFactory() {
    return entityFactory;
  }

  public Interactor loadInteractors(Gateway gateway) {
    return new DivisionInteractor(gateway, getEntityFactory())
                 .append(new ContractTypeInteractor(gateway, getEntityFactory()))
                 .append(new ProfileInteractor(gateway, getEntityFactory()))
                 .append(new CommitteeInteractor(gateway, getEntityFactory()))
                 .append(new QueryInteractor(gateway))
                 .append(new ElectionInteractor(gateway, gateway, gateway, getEntityFactory()));
  }

  public List<ViewableProfile> getProfilesForQuery(String query) {
    return getValues(requestFactory.viewProfilesList(query));
  }

  public List<ViewableCommittee> getAllCommittees() {
    return getValues(requestFactory.viewCommitteeList());
  }

  public boolean editProfile(String username, Map<String, String> changes) {
    return isSuccessful(requestFactory.editProfile(username, changes));
  }

  public boolean addProfile(
        String fullname, String username,
        String contractType, String division
  ) {
    return isSuccessful(requestFactory.createProfile(fullname, username, contractType, division));
  }

  public ViewableProfile getProfile(String username) {
    return getValues(requestFactory.viewProfile(username));
  }

  public boolean addCommittee(String name, String description) {
    return isSuccessful(requestFactory.createCommittee(name, description));
  }

  public boolean addSeat(String committeeName, String seatName, String query) {
    return isSuccessful(requestFactory.addSeat(committeeName, seatName, query));
  }

  public String createElection(String committeeName, String seatName) {
    return getValues(requestFactory.createElection(committeeName, seatName));
  }

  public boolean addDivision(String division) {
    return isSuccessful(requestFactory.addDivision(division));
  }

  public boolean addNamedQuery(String name, String queryString) {
    return isSuccessful(requestFactory.createNamedQuery(name, queryString));
  }

  public Map<String, String> getAllQueries() {
    return getValues(requestFactory.viewQueryList());
  }

  public ViewableValidationResult validateString(String string) {
    return getValues(requestFactory.validateQuery(string));
  }

  public boolean addContractType(String contractType) {
    return isSuccessful(requestFactory.addContractType(contractType));
  }

  public boolean hasDivision(String division) {
    List<String> result = getValues(requestFactory.viewDivisionList());
    return result.contains(division);
  }

  public boolean hasContractType(String contractType) {
    List<String> result = getValues(requestFactory.viewContractTypeList());
    return result.contains(contractType);
  }

  private boolean isSuccessful(Request request) {
    return getResponse(request).isSuccessful();
  }

  private Response getResponse(Request request) {
    return interactor.handle(request);
  }

  private <T> T getValues(Request request) {
    return getResponse(request).getValues();
  }

  public boolean addCandidate(String electionId, String name) {
    return isSuccessful(requestFactory.addCandidate(electionId, name));
  }

  public boolean submitVote(String voterName, String electionID, List<String> vote) {
    return isSuccessful(requestFactory.submitVote(voterName, electionID, vote));
  }

  public ViewableVoteRecord getVoteRecord(String electionId, String voter) {
    return getValues(requestFactory.viewVoteRecord(voter, electionId));
  }

  public List<ViewableVoteRecord> getAllVotes(String electionId) {
    return getValues(requestFactory.viewAllVotes(electionId));
  }
}