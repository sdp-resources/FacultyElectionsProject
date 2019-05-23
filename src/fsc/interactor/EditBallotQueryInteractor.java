package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.request.EditBallotQueryRequest;
import fsc.response.SuccessfullyEditedResponse;
import fsc.response.FailedSearchResponse;

public class EditBallotQueryInteractor{
  private ElectionGateway gateway;
  private Object FailedSearchResponse;

  public EditBallotQueryInteractor(ElectionGateway gateway){
    this.gateway = gateway;
  }

  public Object execute(EditBallotQueryRequest request) throws Exception {
    try {
      Election election = gateway.getElectionFromElectionID(request.electionID);
      BallotCreatorStub ballotCreator = new BallotCreatorStub();
      Ballot ballot = ballotCreator.getBallot(election.getDefaultQuery());
      election.setBallot(ballot);
      //election.setDefaultQuery();

      updateElectionInGateway(election);
      return new SuccessfullyEditedResponse();
    } catch (Exception e) {
      return FailedSearchResponse;
    }

  }

  private void updateElectionInGateway(Election election) {
  }

  private void updateBAllotWithChanges() {
    gateway.save();
  }

}
