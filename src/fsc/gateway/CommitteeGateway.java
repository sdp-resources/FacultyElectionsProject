package fsc.gateway;

import fsc.entity.Committee;
import fsc.entity.Seat;

import java.util.List;

public interface CommitteeGateway {
  List<Committee> getCommittees();
  Committee getCommittee(String name) throws UnknownCommitteeException;
  Seat getSeat(String committeeName, String seatName) throws UnknownCommitteeException,
                                                             UnknownSeatNameException;
  void addCommittee(Committee committee);
  void save();
  boolean hasCommittee(String name);
  void addSeat(Seat seat);

  class UnknownCommitteeException extends Exception {}
  class UnknownSeatNameException extends Exception {}
}
