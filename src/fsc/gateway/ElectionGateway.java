package fsc.gateway;

import fsc.entity.Committee;
import fsc.entity.Election;
import fsc.entity.Seat;

public interface ElectionGateway {
  Seat getSeat(String seatName) throws InvalidSeatNameException;
  void createElection(Election makeElectionFromRequest);
  class InvalidSeatNameException extends Exception {}
}
