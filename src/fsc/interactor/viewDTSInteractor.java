package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.request.viewDTSRequest;
import fsc.response.*;

public class viewDTSInteractor {

  public Gateway gateway;

  public viewDTSInteractor(Gateway gateway){
    this.gateway = gateway;
  }

  public Response execute(viewDTSRequest request) throws ErrorResponse {
    try{
      Profile profile = gateway.getProfile(request.username);
      Ballot ballot = gateway.getBallot(request.electionID.toString());
      ballot.contains(profile);
    }
    catch(Exception e){
      throw new ErrorResponse("No Profile Exist");
    }

    return new SuccessResponse();
  }
}
