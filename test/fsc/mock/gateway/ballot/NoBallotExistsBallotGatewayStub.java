package fsc.mock.gateway.ballot;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class NoBallotExistsBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String ballotID) throws InvalidBallotIDException {
    throw new InvalidBallotIDException();
  }

  public void addBallot(Ballot ballot) {

  }

  public void save() {

  }

}
