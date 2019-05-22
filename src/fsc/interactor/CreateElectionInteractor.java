package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.ElectionGateway;
import fsc.request.CreateElectionRequest;
import fsc.response.*;

public class CreateElectionInteractor {
  public ElectionGateway gateway;

  public CreateElectionInteractor(ElectionGateway gateway) {
    this.gateway = gateway;
  }

  public CreateElectionResponse execute(CreateElectionRequest request) throws Exception {


    try {
      Committee committee = gateway.getCommitteeFromCommitteeName(request.committeeName);
      Seat seat = committee.getSeat(request.seatName);
      Ballot ballot = makeBallotFromSeat(seat);
      gateway.addElection( new Election(seat, committee, seat.getDefaultQuery(), ballot));
      gateway.save();
      return new SuccessfullyCreatedElectionResponse();
    } catch (ElectionGateway.InvalidCommitteeNameException r) { return new FailedToCreateElectionResponse();}
  }

  private Ballot makeBallotFromSeat(Seat seat) {
    BallotCreatorStub ballotCreator = new BallotCreatorStub();
    return ballotCreator.getBallot(seat.getDefaultQuery());
  }

}
