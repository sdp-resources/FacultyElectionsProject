package fsc.mock;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.interactor.CreateElectionInteractor;
import fsc.request.CreateElectionRequest;

import java.util.ArrayList;

public class ElectingWithEverythingCorrectGatewaySpy implements ElectionGateway{

  public ArrayList<Seat> seats = new ArrayList<Seat>();
  public ArrayList<Committee> committees = new ArrayList<Committee>();
  public String submittedCommitteeName;
  public CreateElectionRequest request;
  public Election addedElection;
  public boolean hasSaved = false;

  public ElectingWithEverythingCorrectGatewaySpy(CreateElectionRequest request){
    this.request = request;
  }

  public ArrayList<Seat> getSeats() {
    return seats;
  }

  public Committee getCommitteeFromCommitteeName(String committeeName) throws InvalidCommitteeNameException {
    submittedCommitteeName = committeeName;
    AlwaysTrueQueryStub queryStub = new AlwaysTrueQueryStub();
    Seat seat = new Seat(request.seatName, queryStub);
    Committee committee = new Committee(committeeName, "fake");
    committee.addMember(seat);
    return committee;
  }

  public void save() {
    hasSaved = true;
  }

  public void addElection(Election election) {
    this.addedElection = election;
    addedElection.setID(1);
    hasSaved = false;
  }

  public void recordVote(VoteRecord voteRecord) {

  }

  public Seat addSeat(Seat seat) {
    return null;
  }
}
