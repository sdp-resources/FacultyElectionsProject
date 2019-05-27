package fsc.interactor;

import fsc.gateway.ContractTypeGateway;
import fsc.request.AddContractTypeRequest;
import fsc.request.Request;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class AddContractTypeInteractor extends Interactor {

  private ContractTypeGateway gateway;

  public AddContractTypeInteractor(ContractTypeGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(AddContractTypeRequest request) {
    gateway.addContractType(request.contractType);
    return new SuccessResponse();
  }

  public boolean canHandle(Request request) {
    return request instanceof AddContractTypeRequest;
  }

  public Response execute(Request request) {
    return execute((AddContractTypeRequest) request);
  }
}
