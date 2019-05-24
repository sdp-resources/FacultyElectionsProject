package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.CreateElectionRequest;
import fsc.response.*;

public class CreateElectionInteractor {
  public final ElectionGateway electionGateway;
  private final CommitteeGateway committeeGateway;
  private final ProfileGateway profileGateway;

  public CreateElectionInteractor(ElectionGateway electionGateway,
                                  CommitteeGateway committeeGateway,
                                  ProfileGateway profileGateway) {
    this.electionGateway = electionGateway;
    this.committeeGateway = committeeGateway;
    this.profileGateway = profileGateway;
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
    BallotCreator ballotCreator = new BallotCreator(profileGateway);
    return ballotCreator.getBallot(seat.getDefaultQuery());
  }

}
