package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.AddContractTypeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class AddContractTypeInteractor extends Interactor<AddContractTypeRequest> {

  private ContractTypeGateway gateway;

  public AddContractTypeInteractor(ContractTypeGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(AddContractTypeRequest request) {
    if (gateway.hasContractType(request.contractType)) {
      return ErrorResponse.resourceExists();
    }
    gateway.addContractType(request.contractType);
    gateway.save();
    return new SuccessResponse();
  }
}
