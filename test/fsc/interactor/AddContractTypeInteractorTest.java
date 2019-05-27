package fsc.interactor;

import fsc.mock.gateway.contractType.ContractTypesGatewayStub;
import fsc.request.AddContractTypeRequest;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddContractTypeInteractorTest {

  private static final String EXISTING_CONTRACT = "tenured";
  private static final String ADDED_CONTRACT = "sabbatical";
  private AddContractTypeRequest request;
  private AddContractTypeInteractor interactor;
  private Response response;
  private ContractTypesGatewayStub contractTypeGateWay;

  @Before
  public void setUp() {
    contractTypeGateWay = new ContractTypesGatewayStub(EXISTING_CONTRACT);
  }

  @Test
  public void succesfullyAddedContract() {
    request = new AddContractTypeRequest(ADDED_CONTRACT);
    interactor = new AddContractTypeInteractor(contractTypeGateWay);
    response = interactor.execute(request);
    assertEquals(new SuccessResponse(), response);
    assertTrue(contractTypeGateWay.contractTypes.contains(ADDED_CONTRACT));
  }
}
