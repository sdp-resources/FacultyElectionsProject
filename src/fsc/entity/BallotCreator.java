package fsc.entity;

import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;

public class BallotCreator {

  private ProfileGateway profileGateway;

  public BallotCreator() {}

  public BallotCreator(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }

  public Ballot getBallot(Query query) {
    Ballot ballot = new Ballot();
    addValidProfilesToBallot(query, ballot);
    return ballot;
  }

  private void addValidProfilesToBallot(Query query, Ballot ballot) {
    for (Profile profile : profileGateway.getAllProfiles()) {
      if (query.isProfileValid(profile)) {
        ballot.add(profile);
      }
    }
  }

  public void setProfileGateway(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }
}
