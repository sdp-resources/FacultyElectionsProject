package fsc.interactor;

import fsc.mock.gateway.contractType.ContractTypesGatewayStub;
import fsc.request.ViewContractsRequest;
import fsc.response.ViewResponse;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ViewContractsInteractorTests {

  @Test
  public void existingContractTypesAreReturned() {
    ViewContractsRequest request = new ViewContractsRequest();
    ContractTypesGatewayStub gateway = new ContractTypesGatewayStub("tenure_track", "tenured");
    ContractTypeInteractor interactor = new ContractTypeInteractor(gateway);
    ViewResponse<List<String>> response = interactor.execute(request);

    assertEquals(ViewResponse.ofStrings(gateway.contractTypes), response);
  }
}
