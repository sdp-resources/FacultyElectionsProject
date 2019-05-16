package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class BallotGatewayDummy implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }
}
