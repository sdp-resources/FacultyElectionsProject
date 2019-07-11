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

  public Election createElection(
        Seat seat, Committee committee, Query query,
        Ballot ballot
  ) {return entityFactory.createElection(seat, committee, query, ballot);}

  public Seat createSeat(String name, Query defaultQuery) {
    return entityFactory.createSeat(name, defaultQuery);
  }

  public Vote createVote(Profile... profiles) {return entityFactory.createVote(profiles);}

  public Vote createVote(List<Profile> profiles) {return entityFactory.createVote(profiles);}

  public List<Vote> createVoteSnapshot(List<Vote> votes) {
    return entityFactory.createVoteSnapshot(votes);
  }

  public Voter createVoter(Profile voter, Election election) {
    return entityFactory.createVoter(voter, election);
  }

  public VoteRecord createVoteRecord(
        Voter voter, List<Profile> votes
  ) {return entityFactory.createVoteRecord(voter, votes);}

  public Division createDivision(String newDivisionName) {
    return entityFactory.createDivision(newDivisionName);
  }

  public Committee createCommittee(String name, String description) {
    return entityFactory.createCommittee(name, description);
  }

  public Candidate createCandidate(Profile profile) {return entityFactory.createCandidate(profile);}

  public Profile createProfile(
        String name, String username, String division, String contract
  ) {
    Profile profile = entityFactory.createProfile(name, username, division, contract);
    entityManager.persist(profile);
    return profile;
  }
}
