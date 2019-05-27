package fsc.mock.gateway.ballot;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class BallotGatewayDummy implements BallotGateway {
  public Ballot getBallot(String id) {
    return null;
  }

  public void addBallot(Ballot ballot) {

  }

  public void save() {

  }

}
