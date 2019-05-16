package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class NoBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String ballotID) throws InvalidBallotIDException {
    throw new InvalidBallotIDException();
  }
}
