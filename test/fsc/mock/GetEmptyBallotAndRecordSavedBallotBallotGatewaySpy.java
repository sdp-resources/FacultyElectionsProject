package fsc.mock;

import fsc.entity.Ballot;
import fsc.gateway.BallotGateway;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy implements BallotGateway {
  public Ballot SavedBallot;

  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return new Ballot();
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {
    SavedBallot = ballot;
  }

  public List<ViewableProfile> viewBallot(List<ViewableProfile> list) {
    return null;
  }
}
