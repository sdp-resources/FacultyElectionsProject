package fsc.mock;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

import java.util.ArrayList;

public class ElectionWithExistingSeatNameElectionGatewaySpy implements ElectionGateway {
  private ArrayList<Seat> seats;
  public static AlwaysTrueQueryStub queryStub = new AlwaysTrueQueryStub();
  public static Seat dummySeat = new Seat("a", queryStub);
  public String submittedSeatName;

  public ElectionWithExistingSeatNameElectionGatewaySpy(){
    this.seats = seats;
  }

  public Seat getSeat(String seatName) throws InvalidSeatNameException {
    submittedSeatName = seatName;
    return dummySeat;
  }

  public void addElection(Election makeElectionFromRequest) {

  }

  public void recordVote(VoteRecord voteRecord) {

  }

  public Election getElectionFromElectionID(String electionID) throws Exception {
    return null;
  }

  public Committee getCommitteeFromCommitteeName(String committeeName)
        throws InvalidCommitteeNameException {
    return null;
  }

  public void save() {

  }

}

