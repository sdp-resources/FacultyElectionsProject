package fsc.interactor;

import fsc.gateway.Gateway;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;

public class AddToBallotInteractor {
      public AddToBallotInteractor(Gateway gateway) {

      }

      public static AddToBallotResponse execute(AddToBallotRequest request) {
        String ballotID = request.getBallotID();
        if (ballotID == null) {

        }
        return null;
      }
}
