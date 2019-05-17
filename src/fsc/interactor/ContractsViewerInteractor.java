package fsc.interactor;

import fsc.gateway.ContractsViewerGateway;
import fsc.request.ContractsViewerRequest;
import fsc.response.ContractsViewerResponse;

import java.util.List;

public class  ContractsViewerInteractor {
  private final ContractsViewerGateway gateway;

  public ContractsViewerInteractor(ContractsViewerGateway gateway){
    this.gateway = gateway;
  }

  public ContractsViewerResponse execute(ContractsViewerRequest request) {
    List<String> contracts = gateway.getContractTypes();
    ContractsViewerResponse response = new ContractsViewerResponse(contracts);
    return response;
  }
}
