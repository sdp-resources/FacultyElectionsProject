package fsc.mock.gateway.election;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;

public class GetEmptyBallotBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String id) {
    return new Ballot();
  }

  public void addBallot(Ballot ballot) {

  }

  public void save() {

  }

}
