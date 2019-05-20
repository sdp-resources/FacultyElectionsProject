package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;

public class BallotGatewayDummy implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

  public void remove(Profile profile) throws Ballot.NoProfileInBallotException {

  }
}
