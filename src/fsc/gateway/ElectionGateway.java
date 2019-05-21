package fsc.gateway;

import fsc.entity.Committee;
import fsc.entity.Election;
import fsc.entity.Seat;

public interface ElectionGateway {
  Committee getCommitteeFromCommitteeName(String committeeName) throws InvalidCommitteeNameException;
  class InvalidSeatNameException extends Exception {}
  class InvalidCommitteeNameException extends Exception {}
  void save();
  void addElection(Election election);

}
