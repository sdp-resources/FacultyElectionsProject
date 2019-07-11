package gateway;

import fsc.app.AppContext;
import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.gateway.Gateway;
import fsc.gateway.QueryGateway;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryGateway implements Gateway {

  private final List<Profile> profiles = new ArrayList<>();
  private final List<String> contractTypes = new ArrayList<>();
  private final List<String> divisions = new ArrayList<>();
  private final List<Committee> committees = new ArrayList<>();
  private final List<VoteRecord> voteRecords = new ArrayList<>();
  private final List<Election> elections = new ArrayList<>();
  private final Map<String, Query> queries = new HashMap<>();
  private QueryStringConverter queryStringConverter = new QueryStringConverter();
  private static EntityFactory entityFactory = new SimpleEntityFactory();

  public static Gateway fromJSONFile(String path) {
    try {
      return fromReader(new JSONElectionDataReader(new File(path), entityFactory));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("file not found: " + path);
    }
  }

  private static Gateway fromReader(ElectionDataReader dataReader) {
    Gateway gateway = new InMemoryGateway();
    dataReader.getContractTypes().forEach(gateway::addContractType);
    dataReader.getDivisions().forEach(gateway::addDivision);
    dataReader.getProfiles().forEach(gateway::addProfile);
    dataReader.getCommittees().forEach(gateway::addCommittee);
    return gateway;
  }

  public ArrayList<Profile> getAllProfiles() {
    return new ArrayList<>(profiles);
  }

  public List<Profile> getProfilesMatching(Query query) {
    return profiles.stream().filter(query::isProfileValid).collect(Collectors.toList());
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

  public void addContractType(String contractType) {
    contractTypes.add(contractType);
  }

  public List<String> getAvailableContractTypes() {
    return new ArrayList<>(contractTypes);
  }

  public boolean hasContractType(String contract) {
    return contractTypes.contains(contract);
  }

  public void addDivision(String division) {
    divisions.add(division);
  }

  public Boolean hasDivision(String divisionName) {
    return divisions.contains(divisionName);
  }

  public List<String> getAvailableDivisions() {
    return new ArrayList<>(divisions);
  }

  public Ballot getBallot(String id) {
    return null;
  }

  public void addBallot(Ballot ballot) {}

  public void addElection(Election election) {
    elections.add(election);
  }

  public List<Committee> getCommittees() {
    return committees;
  }

  public Committee getCommittee(String committeeName) throws UnknownCommitteeException {
    for (Committee committee : committees) {
      if (committee.getName().equals(committeeName)) {
        return committee;
      }
    }
    throw new UnknownCommitteeException();
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

  public void recordVote(VoteRecord voteRecord) {
    voteRecords.add(voteRecord);
  }

  public boolean hasVoteRecord(Voter voter) {
    for (VoteRecord record : voteRecords) {
      if (record.isRecordFor(entityFactory.createVoter(voter.getVoter(), voter.getElection()))) return true;
    }

    return false;
  }

  public VoteRecord getVoteRecord(Voter voter) throws NoVoteRecordException {
    for (VoteRecord record : voteRecords) {
      if (record.isRecordFor(AppContext.getEntityFactory().createVoter(voter.getVoter(), voter.getElection()))) return record;
    }

    throw new NoVoteRecordException();
  }

  public Election getElection(String electionID) throws InvalidElectionIDException {
    for (Election election : elections) {
      if (election.getID().equals(electionID)) return election;
    }

    throw new InvalidElectionIDException();
  }

  public List<VoteRecord> getAllVotes(Election election) {
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
    if (hasQuery(name))
      return queries.get(name);
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

  public void save() {
    addIdsToElections();
  }

  private void addIdsToElections() {
    int id = 1;
    for (Election election : elections) {
      election.setID("election" + id);
      id++;
    }
  }

  public Map<String, Query> getAllQueries() {
    return queries;
  }

  public List<Committee> getAllCommittees() {
    return new ArrayList<>(committees);
  }

  public Collection<Election> getAllElections() {
    return new ArrayList<>(elections);
  }

  public EntityFactory getEntityFactory() {
    return entityFactory;
  }
}