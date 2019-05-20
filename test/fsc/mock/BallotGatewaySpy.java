package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;

public class BallotGatewaySpy implements BallotGateway {
  private final Ballot ballot;
  public boolean canGetBallot = false;

  public BallotGatewaySpy() {
    this.ballot = new Ballot();
    this.ballot.add(new Profile("Haris Skiadas", "skiadas", "Natural Science", "Tenured"));
    this.ballot.add(new Profile("Theresa Wilson", "wilson", "Natural Science", "tenure-track"));
    this.ballot.add(new Profile("Barb Wahl", "wahl", "Natural Science", "Tenured"));
    this.ballot.add(new Profile("John Collins", "collins", "Natural Science", "Tenured"));
  }
  public Ballot getBallot(String id) throws InvalidBallotIDException {
    canGetBallot = true;
    return ballot;
  }

  public void saveBallot(Ballot ballot) throws CannotSaveBallotException {

  }

}
