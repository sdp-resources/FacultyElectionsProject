package fsc.interactor;

import fsc.gateway.ContractTypeGateway;

import fsc.request.CreateContractTypeRequest;
import fsc.response.*;

public class CreateContractTypeInteractor {

  private ContractTypeGateway gateway;

  public CreateContractTypeInteractor(ContractTypeGateway gateway){this.gateway = gateway;}

  public Response execute(
        CreateContractTypeRequest request) {
    try
    {
      gateway.addContractType(request.contract_type);
    }
    catch (Exception e) {
      return new ErrorResponse("Contract already exists");
    }
    return new SuccessResponse();
}
  }
