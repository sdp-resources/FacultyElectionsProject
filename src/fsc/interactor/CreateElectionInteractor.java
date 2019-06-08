package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.request.CreateElectionRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class CreateElectionInteractor {
  private ElectionGateway electionGateway;
  private CommitteeGateway committeeGateway;
  private BallotCreator ballotCreator;

  public CreateElectionInteractor(
        ElectionGateway electionGateway, CommitteeGateway committeeGateway,
        BallotCreator ballotCreator
  ) {
    this.electionGateway = electionGateway;
    this.committeeGateway = committeeGateway;
    this.ballotCreator = ballotCreator;
  }

  public Response execute(CreateElectionRequest request) {
    try {
      Committee committee = committeeGateway.getCommittee(request.committeeName);
      Seat seat = committee.getSeat(request.seatName);
      Ballot ballot = ballotCreator.getBallot(seat.getDefaultQuery());
      electionGateway.addElection(new Election(seat, committee, seat.getDefaultQuery(), ballot));
      electionGateway.save();
      return new SuccessResponse();
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ErrorResponse.unknownCommitteeName();
    } catch (Committee.UnknownSeatNameException e) {
      return ErrorResponse.unknownSeatName();
    }
  }

}
