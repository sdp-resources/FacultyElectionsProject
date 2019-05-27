package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.CreateContractTypeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

class CreateContractTypeInteractor {

  private ContractTypeGateway gateway;

  public CreateContractTypeInteractor(ContractTypeGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateContractTypeRequest request) {
    try {
      gateway.addContractType(request.contractType);
      return new SuccessResponse();
    } catch (ContractTypeGateway.ExistingContractTypeException e) {
      return ErrorResponse.resourceExists();
    }
  }
}
