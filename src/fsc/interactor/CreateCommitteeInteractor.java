package fsc.interactor;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.request.CreateCommitteeRequest;
import fsc.response.CreateCommitteeResponse;
import fsc.response.FailedToAddCommitteeResponse;
import fsc.response.SuccessfullyAddedCommitteeResponse;

public class CreateCommitteeInteractor {
  public CommitteeGateway gateway;

  public CreateCommitteeInteractor(CommitteeGateway gateway){
    this.gateway = gateway;
  }

  public CreateCommitteeResponse execute(CreateCommitteeRequest request) {
    try{ gateway.getCommitteeFromCommitteeName(request.name);}
    catch (Exception e) {
      gateway.addCommittee(makeCommitteeFromRequest(request));
      return new SuccessfullyAddedCommitteeResponse();
    }
    return new FailedToAddCommitteeResponse();
  }

  private Committee makeCommitteeFromRequest(CreateCommitteeRequest request) {
    return new Committee(request.name, request.description);
  }
}
