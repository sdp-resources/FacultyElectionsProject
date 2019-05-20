package fsc.gateway;

import fsc.entity.Ballot;
import fsc.viewable.ViewableProfile;

import java.util.List;

public interface BallotGateway {
  Ballot getBallot(String id) throws InvalidBallotIDException;
  void saveBallot(Ballot ballot) throws CannotSaveBallotException;
  List<ViewableProfile> viewBallot(String id);
  class InvalidBallotIDException extends Exception {}
  class CannotSaveBallotException extends Exception {}
}
