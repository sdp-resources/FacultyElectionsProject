package fsc.interactor;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.request.CreateCommitteeRequest;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class CreateCommitteeInteractor extends Interactor<CreateCommitteeRequest> {
  private CommitteeGateway gateway;

  public CreateCommitteeInteractor(CommitteeGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateCommitteeRequest request) {
    if (gateway.hasCommittee(request.name)) {
      return ErrorResponse.resourceExists();
    }
    gateway.addCommittee(makeCommitteeFromRequest(request));
    gateway.save();
    return new SuccessResponse();
  }

  private Committee makeCommitteeFromRequest(CreateCommitteeRequest request) {
    return new Committee(request.name, request.description);
  }

  public boolean canHandle(Request request) {
    return request instanceof CreateCommitteeRequest;
  }
}
