package fsc.interactor;

import fsc.gateway.ContractTypeGateway;

import fsc.request.CreateContractTypeRequest;
import fsc.response.*;

public class CreateContractTypeInteractor {

  private static ContractTypeGateway gateway;

  public CreateContractTypeInteractor(ContractTypeGateway gateway){this.gateway = gateway;}

  public Response execute(
        CreateContractTypeRequest request) {
    try
    {
      gateway.getContractTypeFromProfile(request.contract_type);
    }
    catch (Exception e) {
      gateway.addContractType(makeContractTypeFromRequest(request));
      return new SuccessfullyAddedContractTypeResponse();
    }
    return new FailedAddedContractTypeResponse();
}

  private String makeContractTypeFromRequest(CreateContractTypeRequest request) {
    return request.contract_type;
  }
  }
