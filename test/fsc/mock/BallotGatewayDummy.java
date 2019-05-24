package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;

public class BallotGatewayDummy implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void addBallot(Ballot ballot) throws CannotAddBallotException {

  }

  public void save() {

  }

}
