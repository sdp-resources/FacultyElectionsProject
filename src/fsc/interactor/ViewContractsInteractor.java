package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.ContractsViewerRequest;
import fsc.response.ContractsViewerResponse;

import java.util.List;

public class ViewContractsInteractor {
  private final ContractTypeGateway gateway;

  public ViewContractsInteractor(ContractTypeGateway gateway){
    this.gateway = gateway;
  }

  public ContractsViewerResponse execute(ContractsViewerRequest request) {
    List<String> contracts = gateway.getAvailableContractTypes();
    ContractsViewerResponse response = new ContractsViewerResponse(contracts);
    return response;
  }
}
