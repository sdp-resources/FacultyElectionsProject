package fsc.interactor;

import fsc.mock.gateway.contractType.ContractTypesGatewayStub;
import fsc.request.ContractsViewerRequest;
import fsc.response.ViewResponse;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ViewContractsInteractorTests {

  @Test
  public void existingContractTypesAreReturned() {
    ContractsViewerRequest request = new ContractsViewerRequest();
    ContractTypesGatewayStub gateway = new ContractTypesGatewayStub("tenure_track", "tenured");
    ViewContractsInteractor interactor = new ViewContractsInteractor(gateway);
    ViewResponse<List<String>> response = interactor.execute(request);

    ViewResponse<List<String>> responseList = new ViewResponse<>(gateway.contractTypes);

    assertEquals(response, responseList);
  }
}
