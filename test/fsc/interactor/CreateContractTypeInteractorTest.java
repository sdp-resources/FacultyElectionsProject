package fsc.interactor;

import fsc.mock.NoSuchContractTypeContractTypeGatewaySpy;
import fsc.request.CreateContractTypeRequest;
import fsc.response.Response;
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
  public void testCorrectExecute() throws Exception {
    NoSuchContractTypeContractTypeGatewaySpy gateway = new NoSuchContractTypeContractTypeGatewaySpy();
    interactor = new CreateContractTypeInteractor(gateway);
    response = interactor.execute(request);
    assertEquals(CONTRACT_TYPE, gateway.submittedContractType);
  }

}
