package fsc.mock.gateway.election;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy implements BallotGateway {
  public Ballot SavedBallot;

  public Ballot getBallot(String id) {
    SavedBallot = new Ballot();
    return SavedBallot;
  }

  public void addBallot(Ballot ballot) {
    SavedBallot = ballot;
  }

  public void save() {}
}
