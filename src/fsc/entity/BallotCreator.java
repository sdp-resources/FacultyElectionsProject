package fsc.entity;

import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;

public class BallotCreator {

  private ProfileGateway profileGateway;
  private EntityFactory entityFactory;

  public BallotCreator(ProfileGateway profileGateway, EntityFactory entityFactory) {
    this.profileGateway = profileGateway;
    this.entityFactory = entityFactory;
  }

  public Ballot getBallotFromQuery(Query query) {
    Ballot ballot = entityFactory.createBallot();
    addValidProfilesToBallot(query, ballot);
    return ballot;
  }

  private void addValidProfilesToBallot(Query query, Ballot ballot) {
    for (Profile profile : profileGateway.getAllProfiles()) {
      if (query.isProfileValid(profile)) {
        ballot.add(entityFactory.createCandidate(profile, ballot));
      }
    }
  }
}
