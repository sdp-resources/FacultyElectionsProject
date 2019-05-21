package fsc.interactor;

import fsc.mock.ContractTypesGatewayStub;
import fsc.request.ContractsViewerRequest;
import fsc.response.ContractsViewerResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ViewContractsInteractorTests {

  @Test
  public void RequestTest() {
    ContractsViewerRequest request = new ContractsViewerRequest();
    ContractTypesGatewayStub gateway = new ContractTypesGatewayStub();
    ViewContractsInteractor interactor = new ViewContractsInteractor(gateway);
    ContractsViewerResponse response = interactor.execute(request);

    ArrayList<String> contracts = new ArrayList<>();
    contracts.add("tenure_track");
    contracts.add("tenured");
    contracts.add("admin");
    contracts.add("part-time");
    contracts.add("one-year");

    ContractsViewerResponse responseList = new ContractsViewerResponse(contracts);

    assertEquals(response, responseList);
  }
}
