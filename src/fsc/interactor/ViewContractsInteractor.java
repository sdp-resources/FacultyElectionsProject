package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.ContractsViewerRequest;
import fsc.response.ViewResponse;

import java.util.List;

public class ViewContractsInteractor {
  private ContractTypeGateway gateway;

  public ViewContractsInteractor(ContractTypeGateway gateway) {
    this.gateway = gateway;
  }

  public ViewResponse<List<String>> execute(ContractsViewerRequest request) {
    return ViewResponse.ofStrings(gateway.getAvailableContractTypes());
  }

}
