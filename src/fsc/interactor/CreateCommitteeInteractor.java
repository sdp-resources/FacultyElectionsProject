package fsc.interactor;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.request.CreateCommitteeRequest;
import fsc.response.*;

public class CreateCommitteeInteractor {
  public CommitteeGateway gateway;

  public CreateCommitteeInteractor(CommitteeGateway gateway){
    this.gateway = gateway;
  }

  public Response execute(CreateCommitteeRequest request) {
    try{ gateway.getCommitteeFromCommitteeName(request.name);}
    catch (Exception e) {
      gateway.addCommittee(makeCommitteeFromRequest(request));
      gateway.save();
      return new SuccessResponse();
    }
    return new ErrorResponse("Failed to create committee");
  }

  private Committee makeCommitteeFromRequest(CreateCommitteeRequest request) {
    return new Committee(request.name, request.description);
  }
}
