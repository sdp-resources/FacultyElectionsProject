package dbGateway;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.gateway.Gateway;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DatabaseBackedGateway implements Gateway {
  private final EntityManager entityManager;
  private EntityFactory basicFactory = new SimpleEntityFactory();
  private EntityFactory entityFactory;

  public DatabaseBackedGateway(EntityManager entityManager) {
    this.entityManager = entityManager;
    entityFactory = new PersistingEntityFactory(basicFactory, entityManager);
    begin();
  }

  private void begin() {
    entityManager.getTransaction().begin();
  }

  public void commit() {
    entityManager.getTransaction().commit();
  }

  private <T> T find(Class<T> aClass, Object o) {
    return entityManager.find(aClass, o);
  }

  public void close() {
    if (entityManager.getTransaction().isActive()) {
      entityManager.getTransaction().rollback();
    }
    entityManager.close();
  }

  public void commitAndClose() {
    commit();
    close();
  }

  public void refresh(Object o) {
    entityManager.refresh(o);
  }

  public EntityFactory getEntityFactory() {
    return entityFactory;
  }

  public List<Committee> getCommittees() {
    return null;
  }

  public Committee getCommittee(String name) throws UnknownCommitteeException {
    return null;
  }

  public void addCommittee(Committee committee) {

  }

  public boolean hasCommittee(String name) {
    return false;
  }

  public Collection<Committee> getAllCommittees() {
    return null;
  }

  public void addContractType(String contract) {

  }

  public List<String> getAvailableContractTypes() {
    return null;
  }

  public boolean hasContractType(String contract) {
    return false;
  }

  public List<String> getAvailableDivisions() {
    return null;
  }

  public void addDivision(String division) {

  }

  public Boolean hasDivision(String divisionName) {
    return null;
  }

  public void addElection(Election election) {

  }

  public void recordVote(VoteRecord voteRecord) {

  }

  public boolean hasVoteRecord(Voter voter) {
    return false;
  }

  public VoteRecord getVoteRecord(Voter voter) throws NoVoteRecordException {
    return null;
  }

  public Election getElection(String electionID) throws InvalidElectionIDException {
    return null;
  }

  public List<VoteRecord> getAllVotes(Election election) {
    return null;
  }

  public Collection<Election> getAllElections() {
    return null;
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    Profile profile = find(Profile.class, username);
    if (profile == null) { throw new InvalidProfileUsernameException(); }
    return profile;
  }

  public List<Profile> getAllProfiles() {
    return (List<Profile>) entityManager.createQuery("SELECT p FROM Profile p")
                                        .getResultList();  }

  public void addProfile(Profile profile) {
    entityManager.persist(profile);
  }

  public boolean hasProfile(String username) {
    Profile profile = find(Profile.class, username);
    return profile != null;
  }

  public void addQuery(String name, Query query) {

  }

  public boolean hasQuery(String name) {
    return false;
  }

  public Query getQuery(String name) {
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
}
