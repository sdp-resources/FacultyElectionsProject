package dbGateway;

import fsc.entity.*;
import fsc.entity.query.Query;

import javax.persistence.EntityManager;
import java.util.List;

public class PersistingEntityFactory implements EntityFactory {
  private EntityFactory entityFactory;
  private EntityManager entityManager;

  public PersistingEntityFactory(
        EntityFactory entityFactory, EntityManager entityManager
  ) {
    this.entityFactory = entityFactory;
    this.entityManager = entityManager;
  }

  public Election createElection(Seat seat) {
    return entityFactory.createElection(seat);
  }

  public Seat createSeat(String name, Query defaultQuery, Committee committee) {
    return entityFactory.createSeat(name, defaultQuery, committee);
  }

  public Voter createVoter(Profile voter, Election election) {
    return entityFactory.createVoter(voter, election);
  }

  public VoteRecord createVoteRecord(Election election, List<Profile> votes) {
    return entityFactory.createVoteRecord(election, votes);
  }

  public Division createDivision(String newDivisionName) {
    return entityFactory.createDivision(newDivisionName);
  }

  public ContractType createContractType(String contractType) {
    return entityFactory.createContractType(contractType);
  }

  public Committee createCommittee(
        String name, String description, Query voterQuery
  ) {
    return entityFactory.createCommittee(name, description, voterQuery);
  }

  public Candidate createCandidate(Profile profile, Election election) {
    return entityFactory.createCandidate(profile, election);
  }

  public Profile createProfile(
        String name, String username, String division, String contract
  ) {
    return entityFactory.createProfile(name, username, division, contract);
  }

  public List<Candidate> createBallot() { return entityFactory.createBallot(); }
}
