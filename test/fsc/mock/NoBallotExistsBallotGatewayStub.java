package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;

public class NoBallotExistsBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String ballotID) throws InvalidBallotIDException {
    throw new InvalidBallotIDException();
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

  public void viewBallot(String id) {

  }

  public void remove(Profile profile) throws Ballot.NoProfileInBallotException {

  }
}
