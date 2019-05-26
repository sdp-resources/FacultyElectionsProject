package fsc.interactor;

import fsc.mock.CreateContractTypeGateWaySpy;
import fsc.request.CreateContractTypeRequest;
import fsc.response.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateContractTypeInteractorTest {

  private static final String addedContract = "tenured";
  private CreateContractTypeRequest request;
  private CreateContractTypeInteractor interactor;
  private Response response;

  @Test
  public void succesfullyAddedContract() {
    request =  new CreateContractTypeRequest(addedContract);
    CreateContractTypeGateWaySpy gateway = new CreateContractTypeGateWaySpy();
    interactor = new CreateContractTypeInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(gateway.currentContractTypes.contains(addedContract));
    assert(response instanceof SuccessResponse);
  }

  @Test
  public void contractAlreadyExist() {
    request =  new CreateContractTypeRequest("sabbatical");
    CreateContractTypeGateWaySpy gateway = new CreateContractTypeGateWaySpy();
    interactor = new CreateContractTypeInteractor(gateway);
    response = interactor.execute(request);
    assertEquals(ErrorResponse.resourceExists(), response);
  }
}
