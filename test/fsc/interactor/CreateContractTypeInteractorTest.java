package fsc.interactor;

import fsc.mock.CreatContractTypeGateWaySpy;
import fsc.request.CreateContractTypeRequest;
import fsc.response.FailedAddedContractTypeResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyAddedContractTypeResponse;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CreateContractTypeInteractorTest {

  public static final String addedContract = "tenured";
  CreateContractTypeRequest request;
  CreateContractTypeInteractor interactor;
  Response response;

  @Test
  public void succesfullyAddedContract() throws Exception {
    request =  new CreateContractTypeRequest(addedContract);
    CreatContractTypeGateWaySpy gateway = new CreatContractTypeGateWaySpy();
    interactor = new CreateContractTypeInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(gateway.curentContractTypes.contains(addedContract));
    assert(response instanceof SuccessfullyAddedContractTypeResponse);
  }

  @Test
  public void contractAlreadyExist() throws Exception {
    request =  new CreateContractTypeRequest("sabbatical");
    CreatContractTypeGateWaySpy gateway = new CreatContractTypeGateWaySpy();
    interactor = new CreateContractTypeInteractor(gateway);
    response = interactor.execute(request);
    assert(response instanceof FailedAddedContractTypeResponse);
  }
}
