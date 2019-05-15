package fsc.interactor;

import fsc.entity.Ballot;
import fsc.gateway.Gateway;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;

public class AddToBallotInteractor {
  public AddToBallotGatewayInterface gateway;

      public AddToBallotInteractor(AddToBallotGatewayInterface gateway) { this.gateway = gateway; }

      public AddToBallotResponse execute(AddToBallotRequest request) throws NoBallotException {
        String ballotID = request.getBallotID();
        /*try{
          Ballot ballot = gateway.getBallot(ballotID);

        }*/

        return null;
      }


  private class NoBallotException extends Exception {

  }
}
