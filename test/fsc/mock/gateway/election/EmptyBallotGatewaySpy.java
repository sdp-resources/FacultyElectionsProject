package fsc.mock.gateway.election;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class EmptyBallotGatewaySpy implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    throw new InvalidBallotIDException();
  }

  public void addBallot(Ballot ballot) {

  }

  public void save() {

  }

}
