package fsc.gateway;

import fsc.entity.Ballot;
import fsc.entity.Profile;

public interface BallotGateway {
  Ballot getBallot(String id) throws InvalidBallotIDException;
  void saveBallot(Ballot ballot) throws CannotSaveBallotException;
  class InvalidBallotIDException extends Exception {}
  class CannotSaveBallotException extends Exception {}
}
