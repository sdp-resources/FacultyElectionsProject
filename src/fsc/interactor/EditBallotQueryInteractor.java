package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.BallotCreator;
import fsc.entity.Election;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.EditBallotQueryRequest;
import fsc.response.ErrorResponse;
import fsc.response.SuccessfullyEditedResponse;

public class EditBallotQueryInteractor{
  private final ElectionGateway electionGateway;
  private final ProfileGateway profileGateway;

  public EditBallotQueryInteractor(
        ElectionGateway electionGateway, ProfileGateway profileGateway
  ){
    this.electionGateway = electionGateway;
    this.profileGateway = profileGateway;
  }

  public Object execute(EditBallotQueryRequest request) {
    try {
      Election election = electionGateway.getElectionFromElectionID(request.electionID);
      BallotCreator ballotCreator = new BallotCreator(profileGateway);
      election.setDefaultQuery(request.query);
      Ballot ballot = ballotCreator.getBallot(election.getDefaultQuery());
      election.setBallot(ballot);
      electionGateway.save();
      return new SuccessfullyEditedResponse();
    } catch (Exception e) {
      return ErrorResponse.unknownElectionID();
    }

  }

}
