package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.BallotCreator;
import fsc.entity.Election;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.EditBallotQueryRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class EditBallotQueryInteractor {
  private ElectionGateway electionGateway;
  private ProfileGateway profileGateway;

  EditBallotQueryInteractor(
        ElectionGateway electionGateway, ProfileGateway profileGateway
  ) {
    this.electionGateway = electionGateway;
    this.profileGateway = profileGateway;
  }

  public Response execute(EditBallotQueryRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      BallotCreator ballotCreator = new BallotCreator(profileGateway);
      election.setDefaultQuery(request.query);
      Ballot ballot = ballotCreator.getBallot(election.getDefaultQuery());
      election.setBallot(ballot);
      electionGateway.save();
      return new SuccessResponse();
    } catch (Exception e) {
      return ErrorResponse.unknownElectionID();
    }

  }

}
