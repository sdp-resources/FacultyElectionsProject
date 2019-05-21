package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy implements BallotGateway {
  public Ballot SavedBallot;

  public Ballot getBallot(String id) throws InvalidBallotIDException {
    SavedBallot = new Ballot();
    return SavedBallot;
  }

  public void addBallot(Ballot ballot) throws CannotAddBallotException {
    SavedBallot = ballot;
  }

  public void save()
  {

  }
}
