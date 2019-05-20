package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class BallotWithProfileStub implements BallotGateway {

  public final Ballot ballot;

  public BallotWithProfileStub(Profile profile) {
    ballot = new Ballot();
    ballot.add(profile);
  }

  public Ballot getBallot(String id) throws InvalidBallotIDException {
    return ballot;
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {


  }

  public List<ViewableProfile> viewBallot(List<ViewableProfile> list) {
    return null;
  }
}
