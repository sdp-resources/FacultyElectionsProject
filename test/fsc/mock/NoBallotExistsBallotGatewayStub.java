package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class NoBallotExistsBallotGatewayStub implements BallotGateway {
  public Ballot getBallot(String ballotID) throws InvalidBallotIDException {
    throw new InvalidBallotIDException();
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

  public List<ViewableProfile> viewBallot(List<ViewableProfile> list) {
    return null;
  }

  public void remove(Profile profile) throws Ballot.NoProfileInBallotException {

  }
}
