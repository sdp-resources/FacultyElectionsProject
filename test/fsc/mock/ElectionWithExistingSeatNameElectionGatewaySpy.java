package fsc.mock;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.Seat;
import fsc.gateway.ElectionGateway;

import java.util.ArrayList;

public class ElectionWithExistingSeatNameElectionGatewaySpy implements ElectionGateway {
  private ArrayList<Seat> seats;
  public static Seat dummySeat = new Seat();
  public String submittedSeatName;

  public ElectionWithExistingSeatNameElectionGatewaySpy(){
    this.seats = seats;
  }

  public Seat getSeatFromSeatName(String seatName) throws InvalidSeatNameException {
    submittedSeatName = seatName;
    return dummySeat;
  }

  public void createElection(Election makeElectionFromRequest) {

  }
}
