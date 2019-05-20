package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class EmptyBallotGatewaySpy implements BallotGateway {
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return null;
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

  public List<ViewableProfile> viewBallot(String id) {
    return null;
  }
}
