package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.AddContractTypeRequest;
import fsc.request.ViewContractsRequest;
import fsc.response.*;

import java.util.List;

public class ContractTypeInteractor extends Interactor {

  private ContractTypeGateway gateway;

  public ContractTypeInteractor(ContractTypeGateway gateway) {
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

  public ViewResponse<List<String>> execute(ViewContractsRequest request) {
    return ViewResponse.ofStrings(gateway.getAvailableContractTypes());
  }

}
