package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.request.CreateElectionRequest;
import fsc.response.*;

public class CreateElectionInteractor {
  public ElectionGateway electionGateway;
  private CommitteeGateway committeeGateway;

  public CreateElectionInteractor(ElectionGateway electionGateway, CommitteeGateway committeeGateway) {
    this.electionGateway = electionGateway;
    this.committeeGateway = committeeGateway;
  }

  public Response execute(CreateElectionRequest request) {
    try {
      Committee committee = committeeGateway.getCommitteeFromCommitteeName(request.committeeName);
      Seat seat = committee.getSeat(request.seatName);
      Ballot ballot = makeBallotFromSeat(seat);
      electionGateway.addElection(new Election(seat, committee, seat.getDefaultQuery(), ballot));
      electionGateway.save();
      return new SuccessfullyCreatedElectionResponse();
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ErrorResponse.invalidCommitteeName();
    } catch (Committee.UnknownSeatNameException e) {
      return ErrorResponse.unknownSeatName();
    }
  }

  private Ballot makeBallotFromSeat(Seat seat) {
    BallotCreatorStub ballotCreator = new BallotCreatorStub();
    return ballotCreator.getBallot(seat.getDefaultQuery());
  }

}
