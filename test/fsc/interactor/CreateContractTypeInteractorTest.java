package fsc.interactor;

import fsc.mock.NoSuchContractTypeContractTypeGatewaySpy;
import fsc.request.CreateContractTypeRequest;
import fsc.response.Response;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CreateContractTypeInteractorTest {

  CreateContractTypeRequest request;
  CreateContractTypeInteractor interactor;
  Response response;

  @Before
  public void setup() {
    request =  new CreateContractTypeRequest("tenured");
  }

  @Test
  public void testCorrectExecute() throws Exception {
    NoSuchContractTypeContractTypeGatewaySpy gateway = new NoSuchContractTypeContractTypeGatewaySpy();
    interactor = new CreateContractTypeInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("tenured", gateway.submittedContractType);
    //assertTrue(response instanceof SuccessfullyAddedProfileResponse);
  }

}
