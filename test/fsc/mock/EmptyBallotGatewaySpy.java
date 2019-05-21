package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class EmptyBallotGatewaySpy implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

  public void save() {

  }

}
