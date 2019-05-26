package fsc.interactor;

import fsc.mock.gateway.contractType.ContractTypesGatewayStub;
import fsc.request.CreateContractTypeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateContractTypeInteractorTest {

  private static final String EXISTING_CONTRACT = "tenured";
  private static final String ADDED_CONTRACT = "sabbatical";
  private CreateContractTypeRequest request;
  private CreateContractTypeInteractor interactor;
  private Response response;
  private ContractTypesGatewayStub contractTypeGateWay;

  @Before
  public void setUp() {
    contractTypeGateWay = new ContractTypesGatewayStub(EXISTING_CONTRACT);
  }

  @Test
  public void succesfullyAddedContract() {
    request = new CreateContractTypeRequest(ADDED_CONTRACT);
    interactor = new CreateContractTypeInteractor(contractTypeGateWay);
    response = interactor.execute(request);
    assertTrue(response instanceof SuccessResponse);
    assertTrue(contractTypeGateWay.contractTypes.contains(ADDED_CONTRACT));
  }

  @Test
  public void contractAlreadyExist() {
    request = new CreateContractTypeRequest(EXISTING_CONTRACT);
    interactor = new CreateContractTypeInteractor(contractTypeGateWay);
    response = interactor.execute(request);
    assertEquals(ErrorResponse.resourceExists(), response);
  }
}
