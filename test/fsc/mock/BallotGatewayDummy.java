package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class BallotGatewayDummy implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

  public List<ViewableProfile> viewBallot(List<ViewableProfile> list) {
    return null;
  }

  public void remove(Profile profile) throws Ballot.NoProfileInBallotException {

  }
}
