package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;

public class AddToBallotInteractor {
  public BallotGateway gateway;

      public AddToBallotInteractor(BallotGateway gateway) { this.gateway = gateway; }

      public AddToBallotResponse execute(AddToBallotRequest request) {
        return null;
      }


  public class NoBallotException extends Exception {

  }
}
