package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;

public class AddToBallotInteractor {
  public BallotGateway gateway;

      public AddToBallotInteractor(BallotGateway gateway) { this.gateway = gateway; }

      public AddToBallotResponse execute(AddToBallotRequest request) {
        String ballotID = request.getBallotID();
        /*try{
          Ballot ballot = gateway.getBallot(ballotID);

        }*/

        return null;
      }


  private class NoBallotException extends Exception {

  }
}
