package fsc.interactor;

import fsc.gateway.CommitteeGateway;
import fsc.request.Request;
import fsc.request.ViewCommitteeListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;

public class ViewCommitteeListInteractor extends Interactor<ViewCommitteeListRequest> {
  private CommitteeGateway committeeGateway;

  public ViewCommitteeListInteractor(CommitteeGateway committeeGateway) {
    this.committeeGateway = committeeGateway;
  }

  public Response execute(ViewCommitteeListRequest request) {
    return ViewResponse.ofCommitteeList(committeeGateway.getCommittees());
  }

  public boolean canHandle(Request request) {
    return request instanceof ViewCommitteeListRequest;
  }
}
