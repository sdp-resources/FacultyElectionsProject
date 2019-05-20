package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;

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

  public void viewBallot(String id) {

  }
}
