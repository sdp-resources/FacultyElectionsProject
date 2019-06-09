package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.Request;
import fsc.request.ViewContractsRequest;
import fsc.response.Response;
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

  public boolean canHandle(Request request) {
    return request instanceof ViewContractsRequest;
  }
}
