package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.gateway.ContractTypeGateway;
import fsc.request.AddContractTypeRequest;
import fsc.request.ViewContractsRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;

public class ContractTypeInteractor extends Interactor {

  private ContractTypeGateway gateway;
  private EntityFactory entityFactory;

  public ContractTypeInteractor(ContractTypeGateway gateway, EntityFactory entityFactory) {
    this.gateway = gateway;
    this.entityFactory = entityFactory;
  }

  public Response execute(AddContractTypeRequest request) {
    if (gateway.hasContractType(request.contractType)) {
      return ResponseFactory.resourceExists();
    }
    gateway.addContractType(entityFactory.createContractType(request.contractType));
    gateway.save();
    return ResponseFactory.success();
  }

  public Response execute(ViewContractsRequest request) {
    return ResponseFactory.ofContractTypes(gateway.getAvailableContractTypes());
  }

}
