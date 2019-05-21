package fsc.mock;

import fsc.entity.Election;
import fsc.entity.Seat;
import fsc.gateway.ElectionGateway;

import java.util.ArrayList;

public class NoElectionWithThatSeatNameElectionGatewaySpy implements ElectionGateway{

  public ArrayList<Seat> seats = new ArrayList<Seat>();
  public String submittedSeatName;

  public NoElectionWithThatSeatNameElectionGatewaySpy(){

  }

  public ArrayList<Seat> getSeats() {
    return seats;
  }

  public Seat getSeat(String seatName) throws InvalidSeatNameException {
    submittedSeatName = seatName;
    for (int i = 0; i <= seats.size(); i++){
      if (seats.get(i).toString().equals(seatName)) {
        return seats.get(i);
      }

    }
    throw new InvalidSeatNameException();
  }

  public void addElection(Election makeElectionFromRequest) {

  }

  public Seat addSeat(Seat seat) {
    return null;
  }
}
