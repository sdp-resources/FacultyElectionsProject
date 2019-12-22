package fsc.service;

import fsc.entity.*;
import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.entity.session.Session;
import fsc.gateway.Gateway;
import fsc.mock.EntityStub;
import fsc.request.*;
import fsc.service.query.NameValidator;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AuthorizingRequestVisitorTest {

  private Gateway gateway = new GatewayStub();
  private AuthorizingRequestVisitor authorizer = new AuthorizingRequestVisitor(gateway);

  @Test
  public void viewProfilesListOnlyAuthorizedForAdmins() {
    assertIsAuthorized(new ViewProfilesListRequest(), admin());
    assertIsNotAuthorized(new ViewProfilesListRequest(), user("skiadas"));
    assertIsNotAuthorized(new ViewProfilesListRequest(), unauthenticated());
  }

  @Test
  public void viewProfileOnlyAuthorizedForAdminsOrTheUser() {
    assertIsAuthorized(new ViewProfileRequest("skiadas"), admin());
    assertIsAuthorized(new ViewProfileRequest("skiadas"), user("skiadas"));
    assertIsNotAuthorized(new ViewProfileRequest("skiadas"), user("other"));
    assertIsNotAuthorized(new ViewProfileRequest("skiadas"), unauthenticated());
  }

  @Test
  public void submitVoteRecordAuthorizedForAdminsOrTheUser() {
    assertIsAuthorized(aVoteRecordRequest(), admin());
    assertIsAuthorized(aVoteRecordRequest(), user("skiadas"));
    assertIsNotAuthorized(aVoteRecordRequest(), user("other"));
    assertIsNotAuthorized(aVoteRecordRequest(), unauthenticated());
  }

  @Test
  public void createProfileOnlyAuthorizedForAdmins() {
    assertIsAuthorized(createProfile(), admin());
    assertIsNotAuthorized(createProfile(), user("skiadas"));
    assertIsNotAuthorized(createProfile(), user("other"));
    assertIsNotAuthorized(createProfile(), unauthenticated());
  }

  @Test
  public void viewActiveElectionsOnlyAuthenticatedUsers() {
    assertIsAuthorized(new ViewActiveElectionsRequest(), admin());
    assertIsAuthorized(new ViewActiveElectionsRequest(), user("skiadas"));
    assertIsNotAuthorized(new ViewActiveElectionsRequest(), unauthenticated());
  }

  private void assertIsNotAuthorized(Request request, Session session) {
    request.setSession(session);
    assertEquals(false, authorizer.isAuthorized(request));
  }

  private void assertIsAuthorized(Request request, Session session) {
    request.setSession(session);
    assertEquals(true, authorizer.isAuthorized(request));
  }

  private Session user(String username) {
    return EntityStub.userSession(username);
  }

  private Session admin() {
    return EntityStub.adminSession();
  }

  private Session unauthenticated() {
    return EntityStub.unauthenticatedSession();
  }

  public CreateProfileRequest createProfile() {
    return new CreateProfileRequest("name", "skiadas", null, null);
  }

  private SubmitVoteRecordRequest aVoteRecordRequest() {
    return new SubmitVoteRecordRequest(1, "skiadas", List.of());
  }

  private static class GatewayStub implements Gateway {
    public Profile getProfile(String username) throws InvalidProfileUsernameException {
      return null;
    }

    public List<Profile> getAllProfiles() {
      return null;
    }

    public void addProfile(Profile profile) {

    }

    public boolean hasProfile(String username) {
      return false;
    }

    public void addQuery(String name, Query query) {

    }

    public boolean hasQuery(String name) {
      return false;
    }

    public NamedQuery getNamedQuery(String name) throws UnknownQueryNameException {
      return null;
    }

    public QueryValidationResult validateQueryString(String queryString) {
      return null;
    }

    public void save() {

    }

    public Map<String, Query> getAllQueries() {
      return null;
    }

    public PasswordRecord getPasswordRecordFor(String username) throws UnknownUsernameException {
      return null;
    }

    public boolean hasPasswordRecordFor(String username) {
      return false;
    }

    public void addPasswordRecord(PasswordRecord record) {

    }

    public void addElection(Election election) {

    }

    public void addVoteRecord(VoteRecord voteRecord) {

    }

    public VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException {
      return null;
    }

    public Election getElection(long electionID) throws InvalidElectionIDException {
      return null;
    }

    public Collection<VoteRecord> getAllVotes(Election election) {
      return null;
    }

    public Collection<Election> getAllElections() {
      return null;
    }

    public Voter getVoter(long voterId) throws InvalidVoterException {
      return null;
    }

    public void addVoter(Voter voter) throws ExistingVoterException {

    }

    public void removeCandidate(Candidate candidate) {

    }

    public void removeVoter(Voter voter) {

    }

    public List<Division> getAvailableDivisions() {
      return null;
    }

    public void addDivision(Division division) {

    }

    public Boolean hasDivision(String divisionName) {
      return null;
    }

    public void addContractType(ContractType contractType) {
    }

    public List<ContractType> getAvailableContractTypes() {
      return null;
    }

    public boolean hasContractType(String contract) {
      return false;
    }

    public List<Committee> getCommittees() {
      return null;
    }

    public Committee getCommitteeByName(String name) throws UnknownCommitteeException {
      return null;
    }

    public Committee getCommittee(Long id) throws UnknownCommitteeException {
      return null;
    }

    public Seat getSeat(Long seatId) throws UnknownSeatNameException {
      return null;
    }

    public Seat getSeatByCommitteeAndSeatName(String committeeName, String seatName)
          throws UnknownCommitteeException, UnknownSeatNameException {
      return null;
    }

    public void addCommittee(Committee committee) {

    }

    public boolean hasCommittee(String name) {
      return false;
    }

    public void addSeat(Seat seat) {

    }

    public EntityFactory getEntityFactory() {
      return null;
    }

    public void begin() {

    }

    public void commit() {

    }

    public void close() {

    }

    public void rollback() {

    }

    public void shutdown() {

    }

    public NameValidator getNameValidator() {
      return null;
    }
  }
}