package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class AddToBallotInteractor {
  public BallotGateway gateway;

      public AddToBallotInteractor(BallotGateway gateway) { this.gateway = gateway; }

      public Response execute(AddToBallotRequest request) {
        return new ErrorResponse("No ballot");
      }
}
