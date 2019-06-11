package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.ViewContractsRequest;
import fsc.response.ViewResponse;

import java.util.List;

public class ViewContractsInteractor extends Interactor<ViewContractsRequest> {
  private ContractTypeGateway gateway;

  public ViewContractsInteractor(ContractTypeGateway gateway) {
    this.gateway = gateway;
  }

  public ViewResponse<List<String>> execute(ViewContractsRequest request) {
    return ViewResponse.ofStrings(gateway.getAvailableContractTypes());
  }
}
