package fsc.gateway;

import fsc.entity.Ballot;

public interface BallotGateway {
  Ballot getBallot(String id) throws InvalidBallotIDException;
  void addBallot(Ballot ballot) throws CannotAddBallotException;
  void save();
  class InvalidBallotIDException extends Exception {}
  class CannotAddBallotException extends Exception {}
}
