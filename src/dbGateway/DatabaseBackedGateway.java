package dbGateway;

import fsc.entity.*;
import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.Gateway;
import fsc.service.query.*;

import javax.persistence.EntityManager;
import java.util.*;

public class DatabaseBackedGateway implements Gateway {
  private final EntityManager entityManager;
  private EntityFactory basicFactory = new SimpleEntityFactory();
  private EntityFactory entityFactory;
  private NameValidator dbValidator = new GatewayBackedQueryValidator(this);

  public DatabaseBackedGateway(EntityManager entityManager) {
    this.entityManager = entityManager;
    entityFactory = new PersistingEntityFactory(basicFactory, entityManager);
  }

  public void begin() {
    if (!entityManager.getTransaction().isActive()) {
      entityManager.getTransaction().begin();
    }
  }

  public void commit() {
    begin();
    entityManager.getTransaction().commit();
  }

  private <T> T find(Class<T> aClass, Object o) {
    return entityManager.find(aClass, o);
  }

  public void close() {
    rollback();
    entityManager.close();
  }

  public void rollback() {
    if (entityManager.getTransaction().isActive()) {
      entityManager.getTransaction().rollback();
    }
  }

  public void shutdown() {
    entityManager.close();
  }

  public void refresh(Object o) {
    entityManager.refresh(o);
  }

  public EntityFactory getEntityFactory() {
    return entityFactory;
  }

  public List<Committee> getCommittees() {
    return (List<Committee>) entityManager.createQuery("SELECT c FROM Committee c")
                                          .getResultList();
  }

  public Committee getCommittee(String name) throws UnknownCommitteeException {
    Committee committee = find(Committee.class, name);
    if (committee == null) { throw new UnknownCommitteeException(); }
    return committee;
  }

  public Seat getSeat(String committeeName, String seatName)
        throws UnknownCommitteeException, UnknownSeatNameException {
    Committee committee = getCommittee(committeeName);
    return committee.getSeat(seatName);
  }

  public void addCommittee(Committee committee) {
    persist(committee);
  }

  public boolean hasCommittee(String name) {
    Committee committee = find(Committee.class, name);
    return committee != null;
  }

  public void addSeat(Seat seat) {
    persist(seat);
  }

  private void persist(Object o) {
    begin();
    entityManager.persist(o);
  }

  public void addContractType(ContractType contractType) {
    persist(contractType);
  }

  public List<ContractType> getAvailableContractTypes() {
    return (List<ContractType>) entityManager.createQuery("SELECT c FROM ContractType c")
                                             .getResultList();
  }

  public boolean hasContractType(String contract) {
    ContractType contractType = find(ContractType.class, contract);
    return contractType != null;
  }

  public List<Division> getAvailableDivisions() {
    return (List<Division>) entityManager.createQuery("SELECT d FROM Division d")
                                         .getResultList();
  }

  public void addDivision(Division division) {
    persist(division);
  }

  public Boolean hasDivision(String divisionName) {
    Division division = find(Division.class, divisionName);
    return division != null;
  }

  public void addElection(Election election) {
    persist(election);
  }

  public void addVoteRecord(VoteRecord voteRecord) {
    persist(voteRecord);
  }

  public VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException {
    VoteRecord voteRecord = find(VoteRecord.class, recordId);
    if (voteRecord == null) { throw new NoVoteRecordException(); }
    return voteRecord;
  }

  public Election getElection(long electionID) throws InvalidElectionIDException {
    Election election = find(Election.class, electionID);
    if (election == null) { throw new InvalidElectionIDException(); }
    return election;
  }

  public Collection<VoteRecord> getAllVotes(Election election) {
    entityManager.refresh(election);
    return election.getVoteRecords();
  }

  public Collection<Election> getAllElections() {
    return entityManager.createQuery("SELECT e FROM Election e")
                        .getResultList();
  }

  public Voter getVoter(long voterId) throws InvalidVoterException {
    Voter voter = find(Voter.class, voterId);
    if (voter == null) { throw new InvalidVoterException(); }
    return voter;
  }

  public void addVoter(Voter voter) {
    persist(voter);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    Profile profile = find(Profile.class, username);
    if (profile == null) { throw new InvalidProfileUsernameException(); }
    return profile;
  }

  public List<Profile> getAllProfiles() {
    return entityManager.createQuery("SELECT p FROM Profile p")
                        .getResultList();
  }

  public void addProfile(Profile profile) {
    persist(profile);
  }

  public boolean hasProfile(String username) {
    Profile profile = find(Profile.class, username);
    return profile != null;
  }

  public void addQuery(String name, Query query) {
    persist(new NamedQuery(name, query));
  }

  public boolean hasQuery(String name) {
    NamedQuery query = find(NamedQuery.class, name);
    return query != null;

  }

  public Query getQuery(String name) {
    NamedQuery query = find(NamedQuery.class, name);

    return query.query;
  }

  public QueryValidationResult validateQueryString(String queryString) {
    try {
      QueryStringParserFactory parserFactory = new ValidatingQueryStringParserFactory(dbValidator);
      QueryStringConverter queryStringConverter = new QueryStringConverter(parserFactory);
      Query query = queryStringConverter.fromString(queryString);
      String string = queryStringConverter.toString(query);
      return new QueryValidationResult.ValidQueryResult(query, string);
    } catch (QueryStringParser.QueryParseException e) {
      return new QueryValidationResult.InvalidQueryResult(e.getMessage());
    }

  }

  public PasswordRecord getPasswordRecordFor(String username) throws UnknownUsernameException {
    PasswordRecord record = find(PasswordRecord.class, username);
    if (record == null) { throw new UnknownUsernameException(); }

    return record;
  }

  public boolean hasPasswordRecordFor(String username) {
    PasswordRecord record = find(PasswordRecord.class, username);
    return record != null;
  }

  public void addPasswordRecord(PasswordRecord record) {
    persist(record);
  }

  public void addSession(AuthenticatedSession session) {
    persist(session);
  }

  public AuthenticatedSession getSession(String token) throws InvalidOrExpiredTokenException {
    AuthenticatedSession session = find(AuthenticatedSession.class, token);
    if (session == null) throw new InvalidOrExpiredTokenException();

    return session;
  }

  public void save() {
    commit();
  }

  public Map<String, Query> getAllQueries() {
    List<NamedQuery> results = entityManager.createQuery("SELECT q FROM NamedQuery q")
                                            .getResultList();
    HashMap<String, Query> resultsMap = new HashMap<>();
    for (NamedQuery result : results) {
      resultsMap.put(result.name, result.query);
    }

    return resultsMap;
  }

  public NameValidator getNameValidator() {
    return dbValidator;
  }
}
