package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class AlwaysFailsSaveBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return new Ballot();
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {
    throw new CannotSaveBallotException();
  }
}
