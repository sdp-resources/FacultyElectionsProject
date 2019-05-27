package fsc.mock.gateway.ballot;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;

import java.util.ArrayList;
import java.util.List;

public class ExistingBallotGatewaySpy implements BallotGateway {
  private final Ballot ballot;
  public String requestedBallotId = null;

  public ExistingBallotGatewaySpy(Ballot ballot) {
    this.ballot = ballot;
  }

  public Ballot getBallot(String id) {
    requestedBallotId = id;
    return ballot;
  }

  public void addBallot(Ballot ballot) {}

  public void save() {}
}
