package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.BallotCreator;
import fsc.entity.Election;
import fsc.gateway.ElectionGateway;
import fsc.request.EditBallotQueryRequest;
import fsc.response.ErrorResponse;
import fsc.response.SuccessfullyEditedResponse;

public class EditBallotQueryInteractor{
  private ElectionGateway gateway;

  public EditBallotQueryInteractor(ElectionGateway gateway){
    this.gateway = gateway;
  }

  public Object execute(EditBallotQueryRequest request) {
    try {
      Election election = gateway.getElectionFromElectionID(request.electionID);
      BallotCreator ballotCreator = new BallotCreator();
      election.setDefaultQuery(request.query);
      Ballot ballot = ballotCreator.getBallot(election.getDefaultQuery());
      election.setBallot(ballot);
      gateway.save();
      return new SuccessfullyEditedResponse();
    } catch (Exception e) {
      return ErrorResponse.unknownElectionID();
    }

  }

}
