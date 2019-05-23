package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.ContractsViewerRequest;
import fsc.response.ViewContractsResponse;

import java.util.List;

public class ViewContractsInteractor {
  private final ContractTypeGateway gateway;

  public ViewContractsInteractor(ContractTypeGateway gateway){
    this.gateway = gateway;
  }

  public ViewContractsResponse execute(ContractsViewerRequest request) {
    List<String> contracts = gateway.getAvailableContractTypes();
    ViewContractsResponse response = new ViewContractsResponse(contracts);
    return response;
  }
}
