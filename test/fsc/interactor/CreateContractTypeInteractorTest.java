package fsc.interactor;

import fsc.mock.succesfullyAddedContractTypeSpy;
import fsc.request.CreateContractTypeRequest;
import fsc.response.Response;
import fsc.response.SuccessfullyAddedContractTypeResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CreateContractTypeInteractorTest {

  public static final String CONTRACT_TYPE = "tenured";
  CreateContractTypeRequest request;
  CreateContractTypeInteractor interactor;
  Response response;

  @Before
  public void setup() {
    request =  new CreateContractTypeRequest(CONTRACT_TYPE);
  }

  @Test
  public void succesfullyAddedContract() throws Exception {
    succesfullyAddedContractTypeSpy gateway = new succesfullyAddedContractTypeSpy();
    interactor = new CreateContractTypeInteractor(gateway);
    response = interactor.execute(request);
    assertEquals(CONTRACT_TYPE, gateway.submittedContractType);
    assert(response instanceof SuccessfullyAddedContractTypeResponse);
  }

}
