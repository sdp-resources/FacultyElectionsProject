package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.gateway.DivisionGateway;
import fsc.request.AddDivisionRequest;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;

public class DivisionInteractor extends Interactor {

  private DivisionGateway gateway;
  private EntityFactory entityFactory;

  public DivisionInteractor(DivisionGateway gateway, EntityFactory entityFactory) {
    this.gateway = gateway;
    this.entityFactory = entityFactory;
  }

  public Response execute(AddDivisionRequest request) {
    if (gateway.hasDivision(request.name)) {
      return ResponseFactory.resourceExists();
    }
    gateway.addDivision(entityFactory.createDivision(request.name));
    gateway.save();
    return ResponseFactory.success();
  }

  public Response execute(ViewDivisionRequest request) {
    return ResponseFactory.ofDivisions(gateway.getAvailableDivisions());
  }
}