package fsc.interactor;

import fsc.mock.ExistingDivisionGatewaySpy;
import fsc.mock.MissingDivisionGatewaySpy;
import fsc.request.AddDivisionRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddDivisionInteractorTest {
  private AddDivisionRequest request;
  private AddDivisionInteractor interactor;

  @Before
  public void setup() {
    request = new AddDivisionRequest("ENG");
  }

  @Test
  public void testCorrectExecute() {
    MissingDivisionGatewaySpy testGateway = new MissingDivisionGatewaySpy();
    interactor = new AddDivisionInteractor(testGateway);
    Response response = interactor.execute(request);
    assertEquals("ENG", testGateway.submittedDivisionName);
    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void testAlreadyExistsExecute() {
    ExistingDivisionGatewaySpy testGateway = new ExistingDivisionGatewaySpy();
    interactor = new AddDivisionInteractor(testGateway);
    Response response = interactor.execute(request);
    assertEquals("ENG", testGateway.submittedDivisionName);
    assertEquals(ErrorResponse.resourceExists(), response);
  }
}
