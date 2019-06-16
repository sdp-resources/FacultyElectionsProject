package fsc.interactor;

import fsc.mock.gateway.contractType.ContractTypesGatewayStub;
import fsc.request.ViewContractsRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewContractsInteractorTests {

  @Test
  public void existingContractTypesAreReturned() {
    ViewContractsRequest request = new ViewContractsRequest();
    ContractTypesGatewayStub gateway = new ContractTypesGatewayStub("tenure_track", "tenured");
    ContractTypeInteractor interactor = new ContractTypeInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.ofStrings(gateway.contractTypes), response);
  }
}
