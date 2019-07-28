package gateway;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.Gateway;
import fsc.gateway.QueryGateway;
import fsc.service.query.*;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryGateway implements Gateway {

  private final List<Profile> profiles = new ArrayList<>();
  private final List<ContractType> contractTypes = new ArrayList<>();
  private final List<Division> divisions = new ArrayList<>();
  private final List<Committee> committees = new ArrayList<>();
  private final List<VoteRecord> voteRecords = new ArrayList<>();
  private final List<Election> elections = new ArrayList<>();
  private final Map<String, Query> queries = new HashMap<>();
  private static EntityFactory entityFactory = new SimpleEntityFactory();
  private long seatId = 0;
  private List<Voter> voters = new ArrayList<>();
  private long voterId = 0;
  private long voteRecordId = 0;
  private long electionId = 0;
  private AcceptingNameValidator nameValidator = new AcceptingNameValidator();
  private QueryStringConverter queryStringConverter = new QueryStringConverter(
        new ValidatingQueryStringParserFactory(nameValidator));

  public static Gateway fromJSONFile(String path) {
      InputStream stream = InMemoryGateway.class.getResourceAsStream(path);
      return fromReader(new JSONElectionDataReader(stream, entityFactory));
  }

  private static Gateway fromReader(ElectionDataReader dataReader) {
    Gateway gateway = new InMemoryGateway();
    EntityFactory entityFactory = gateway.getEntityFactory();
    dataReader.getContractTypes()
              .map(entityFactory::createContractType)
              .forEach(gateway::addContractType);
    dataReader.getDivisions()
              .map(entityFactory::createDivision)
              .forEach(gateway::addDivision);
    dataReader.getProfiles().forEach(gateway::addProfile);
    dataReader.getCommittees().forEach(gateway::addCommittee);
    return gateway;
  }

  public ArrayList<Profile> getAllProfiles() {
    return new ArrayList<>(profiles);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    for (Profile currProfile : profiles) {
      if (username.equals(currProfile.getUsername())) return currProfile;
    }
    throw new InvalidProfileUsernameException();
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  public boolean hasProfile(String username) {
    for (Profile currProfile : profiles) {
      if (username.equals(currProfile.getUsername())) return true;
    }
    return false;
  }

  public void addContractType(ContractType contractType) {
    contractTypes.add(contractType);
  }

  public List<ContractType> getAvailableContractTypes() {
    return new ArrayList<>(contractTypes);
  }

  public boolean hasContractType(String contract) {
    for (ContractType contractType : contractTypes) {
      if (contractType.getContract().equals(contract)) return true;
    }
    return false;
  }

  public void addDivision(Division division) {
    divisions.add(division);
  }

  public Boolean hasDivision(String divisionName) {
    for (Division division : divisions) {
      if (division.getName().equals(divisionName)) { return true; }
    }
    return false;
  }

  public List<Division> getAvailableDivisions() {
    return new ArrayList<>(divisions);
  }

  public Collection<Candidate> getBallot(String id) {
    return null;
  }

  public void addBallot(Collection<Candidate> ballot) {
    // TODO
  }

  public void addElection(Election election) {
    election.setID(electionId++);
    elections.add(election);
  }

  public List<Committee> getCommittees() {
    return new ArrayList<>(committees);
  }

  public Committee getCommittee(String committeeName) throws UnknownCommitteeException {
    for (Committee committee : committees) {
      if (committee.getName().equals(committeeName)) {
        return committee;
      }
    }
    throw new UnknownCommitteeException();
  }

  public Seat getSeat(String committeeName, String seatName)
        throws UnknownCommitteeException, UnknownSeatNameException {
    Committee committee = getCommittee(committeeName);
    return committee.getSeat(seatName);
  }

  public void addCommittee(Committee committee) {
    committees.add(committee);
  }

  public boolean hasCommittee(String name) {
    for (Committee committee : committees) {
      if (committee.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public void addSeat(Seat seat) {
    seat.setId(seatId++);
  }

  public void addVoteRecord(VoteRecord voteRecord) {
    voteRecords.add(voteRecord);
    voteRecord.setRecordId(voteRecordId++);
  }

  public VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException {
    for (VoteRecord record : voteRecords) {
      if (record.getRecordId().equals(recordId)) {
        return record;
      }
    }

    throw new NoVoteRecordException();
  }

  public Election getElection(long electionID) throws InvalidElectionIDException {
    for (Election election : elections) {
      if (election.getID().equals(electionID)) { return election; }
    }

    throw new InvalidElectionIDException();
  }

  public Collection<VoteRecord> getAllVotes(Election election) {
    return voteRecords.stream().filter(r -> r.getElection().equals(election))
                      .collect(Collectors.toList());
  }

  public void addQuery(String name, Query query) {
    queries.put(name, query);
  }

  public boolean hasQuery(String name) {
    return queries.containsKey(name);
  }

  public Query getQuery(String name) throws UnknownQueryNameException {
    if (hasQuery(name)) { return queries.get(name); }
    throw new QueryGateway.UnknownQueryNameException();
  }

  public QueryValidationResult validateQueryString(String queryString) {
    try {
      Query query = queryStringConverter.fromString(queryString);
      String string = queryStringConverter.toString(query);
      return new QueryValidationResult.ValidQueryResult(query, string);
    } catch (QueryStringParser.QueryParseException e) {
      return new QueryValidationResult.InvalidQueryResult(e.getMessage());
    }
  }

  public PasswordRecord getPasswordRecordFor(String username) throws UnknownUsernameException {
    // TODO
    return null;
  }

  public boolean hasPasswordRecordFor(String username) {
    // TODO
    return false;
  }

  public void addPasswordRecord(PasswordRecord record) {
    // TODO
  }

  public void addSession(AuthenticatedSession session) {
    // TODO
  }

  public AuthenticatedSession getSession(String token) throws InvalidOrExpiredTokenException {
    // TODO
    return null;
  }

  public void save() {
    addIdsToElections();
  }

  private void addIdsToElections() {
    // TODO
  }

  public Map<String, Query> getAllQueries() {
    return queries;
  }

  public Collection<Election> getAllElections() {
    return new ArrayList<>(elections);
  }

  public Voter getVoter(long voterId) throws InvalidVoterException {
    for (Voter voter : voters) {
      if (voter.getVoterId() == voterId) {
        return voter;
      }
    }
    throw new InvalidVoterException();
  }

  public void addVoter(Voter voter) throws ExistingVoterException {
    if (hasVoter(voter.getProfile(), voter.getElection())) {
      throw new ExistingVoterException();
    }
    voters.add(voter);
    voter.setVoterId(voterId++);
    voter.getElection().addVoter(voter);
  }

  private boolean hasVoter(Profile profile, Election election) {
    return election.getVoter(profile) != null;
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
    return nameValidator;
  }

  public EntityFactory getEntityFactory() {
    return entityFactory;
  }
}