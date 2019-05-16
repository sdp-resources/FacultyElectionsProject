package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class GetBallotFromIDBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return new Ballot();
  }
}
