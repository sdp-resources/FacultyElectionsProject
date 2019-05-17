package fsc.gateway;

import fsc.entity.Committee;
import fsc.entity.Election;
import fsc.entity.Seat;

public interface ElectionGateway {
  void addElection(Election makeElectionFromRequest);
  Seat getSeatFromSeatName(String seatName);
}
