package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.request.CreateElectionRequest;
import fsc.request.EditBallotQueryRequest;
import fsc.request.ViewCandidatesRequest;
import fsc.response.*;

public class ElectionInteractor {
  private ElectionGateway electionGateway;
  private CommitteeGateway committeeGateway;
  private BallotCreator ballotCreator;

  public ElectionInteractor(ElectionGateway electionGateway, BallotCreator ballotCreator) {
    this(electionGateway, null, ballotCreator);
  }

  public ElectionInteractor(
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

  public Response execute(EditBallotQueryRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      election.setDefaultQuery(request.query);
      Ballot ballot = ballotCreator.getBallot(election.getDefaultQuery());
      election.setBallot(ballot);
      electionGateway.save();
      return new SuccessResponse();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

  public Response execute(ViewCandidatesRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      return ViewResponse.ofProfileList(election.getCandidateProfiles());
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }
}
