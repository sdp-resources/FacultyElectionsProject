package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class GetEmptyBallotBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return new Ballot();
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

  public void viewBallot(String id) {

  }
}
