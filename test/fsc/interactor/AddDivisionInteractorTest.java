package fsc.interactor;

import fsc.mock.gateway.division.ExistingDivisionGatewaySpy;
import fsc.mock.gateway.division.MissingDivisionGatewaySpy;
import fsc.request.AddDivisionRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddDivisionInteractorTest {
  public static final String A_DIVISION = "ENG";
  private AddDivisionRequest request;
  private AddDivisionInteractor interactor;

  @Before
  public void setup() {
    request = new AddDivisionRequest(A_DIVISION);
  }

  @Test
  public void testCorrectExecute() {
    MissingDivisionGatewaySpy testGateway = new MissingDivisionGatewaySpy();
    interactor = new AddDivisionInteractor(testGateway);
    Response response = interactor.execute(request);
    assertEquals(A_DIVISION, testGateway.submittedDivisionName);
    assertEquals(new SuccessResponse(), response);
    assertTrue(testGateway.saveCalled);
  }

  @Test
  public void testAlreadyExistsExecute() {
    ExistingDivisionGatewaySpy testGateway = new ExistingDivisionGatewaySpy();
    interactor = new AddDivisionInteractor(testGateway);
    Response response = interactor.execute(request);
    assertEquals(A_DIVISION, testGateway.submittedDivisionName);
    assertEquals(ErrorResponse.resourceExists(), response);
  }
}
