package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.AddContractTypeRequest;
import fsc.request.ViewContractsRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;

public class ContractTypeInteractor extends Interactor {

  private ContractTypeGateway gateway;

  public ContractTypeInteractor(ContractTypeGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(AddContractTypeRequest request) {
    if (gateway.hasContractType(request.contractType)) {
      return ResponseFactory.resourceExists();
    }
    gateway.addContractType(request.contractType);
    gateway.save();
    return ResponseFactory.success();
  }

  public Response execute(ViewContractsRequest request) {
    return ResponseFactory.ofStrings(gateway.getAvailableContractTypes());
  }

}
