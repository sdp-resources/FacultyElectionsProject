package fsc.entity;

import fsc.gateway.Gateway;
import fsc.query.Query;

public class BallotCreator {

  private Gateway gateway;

  public Ballot getBallot(Query query) {
    Ballot ballot = new Ballot();
    addValidProfilesToBallot(query, ballot);
    return ballot;
  }

  private void addValidProfilesToBallot(Query query, Ballot ballot) {
    for (Profile profile : gateway.getAllProfiles()) {
      if (query.isProfileValid(profile)) {
        ballot.add(profile);
      }
    }
  }

  public void setGateway(Gateway gateway) {
    this.gateway = gateway;
  }
}
