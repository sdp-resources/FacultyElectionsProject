package fsc.interactor;

import fsc.entity.Ballot;

public class BallotGatewaySpy implements AddToBallotGatewayInterface {
  public Ballot getBallot(String id) {
    return null;
  }
}
