package dbGateway;

import fsc.entity.*;
import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.gateway.Gateway;
import fsc.service.query.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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

  public Committee getCommitteeByName(String name) throws UnknownCommitteeException {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Committee> q = cb.createQuery(Committee.class);
    Root<Committee> c = q.from(Committee.class);
    ParameterExpression<String> nameParam = cb.parameter(String.class);
    q.select(c).where(cb.equal(c.get("name"), nameParam));
    TypedQuery<Committee> query = entityManager.createQuery(q);
    query.setParameter(nameParam, name);

    List<Committee> resultList = query.getResultList();
    if (resultList.isEmpty()) { throw new UnknownCommitteeException(); }
    return resultList.get(0);
  }

  public Committee getCommittee(Long id) throws UnknownCommitteeException {
    Committee committee = find(Committee.class, id);
    if (committee == null) { throw new UnknownCommitteeException(); }
    return committee;
  }

  public Seat getSeat(Long seatId) throws UnknownSeatNameException {
    Seat seat = find(Seat.class, seatId);
    if (seat == null) { throw new UnknownSeatNameException(); }
    return seat;
  }

  public Seat getSeatByCommitteeAndSeatName(String committeeName, String seatName)
        throws UnknownCommitteeException, UnknownSeatNameException {
    Committee committee = getCommitteeByName(committeeName);
    return committee.getSeat(seatName);
  }

  public void addCommittee(Committee committee) {
    persist(committee);
  }

  public boolean hasCommittee(String name) {
    //TODO: Cleanup
    try {
      getCommitteeByName(name);
      return true;
    } catch (UnknownCommitteeException e) {
      return false;
    }
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
    return entityManager.createQuery("SELECT c FROM ContractType c")
                        .getResultList();
  }

  public boolean hasContractType(String contract) {
    ContractType contractType = find(ContractType.class, contract);
    return contractType != null;
  }

  public List<Division> getAvailableDivisions() {
    return entityManager.createQuery("SELECT d FROM Division d")
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
    refresh(election);
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

  public void addVoter(Voter voter) throws ExistingVoterException {
    if (voterExists(voter.getElection(), voter.getProfile())) {
      throw new ExistingVoterException();
    }
    persist(voter);
  }

  private boolean voterExists(Election election, Profile profile) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Voter> q = cb.createQuery(Voter.class);
    Root<Voter> c = q.from(Voter.class);
    ParameterExpression<Profile> profileParam = cb.parameter(Profile.class);
    ParameterExpression<Election> electionParam = cb.parameter(Election.class);
    Predicate[] predicates = new Predicate[2];
    predicates[0] = cb.equal(c.get("election"), electionParam);
    predicates[1] = cb.equal(c.get("profile"), profileParam);
    q.select(c).where(predicates);
    TypedQuery<Voter> query = entityManager.createQuery(q);
    query.setParameter(electionParam, election);
    query.setParameter(profileParam, profile);

    List<Voter> resultList = query.getResultList();

    return !resultList.isEmpty();
  }

  public void removeCandidate(Candidate candidate) {
    Election election = candidate.getElection();
    entityManager.remove(candidate);
    election.removeCandidate(candidate.getProfile());
  }

  public void removeVoter(Voter voter) {
    Election election = voter.getElection();
    entityManager.remove(voter);
    election.removeVoter(voter);
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

  public NamedQuery getNamedQuery(String name) throws UnknownQueryNameException {
    NamedQuery query = find(NamedQuery.class, name);
    if (query == null) { throw new UnknownQueryNameException(); }
    return query;
  }

  public QueryValidationResult validateQueryString(String queryString) {
    QueryStringParserFactory parserFactory = new ValidatingQueryStringParserFactory(dbValidator);
    QueryStringConverter queryStringConverter = new QueryStringConverter(parserFactory);
    return queryStringConverter.validateQueryString(queryString);
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
