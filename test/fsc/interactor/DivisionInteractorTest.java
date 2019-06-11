package fsc.interactor;

import fsc.mock.gateway.division.ExistingDivisionGatewaySpy;
import fsc.mock.gateway.division.MissingDivisionGatewaySpy;
import fsc.request.AddDivisionRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DivisionInteractorTest {
  private static final String A_DIVISION = "ENG";
  private AddDivisionRequest request;
  private DivisionInteractor interactor;

  @Before
  public void setup() {
    request = new AddDivisionRequest(A_DIVISION);
  }

  @Test
  public void testCorrectExecute() {
    MissingDivisionGatewaySpy testGateway = new MissingDivisionGatewaySpy();
    interactor = new DivisionInteractor(testGateway);
    Response response = interactor.execute(request);
    assertEquals(new SuccessResponse(), response);
    assertEquals(List.of("has division: " + A_DIVISION, "add division: " + A_DIVISION, "save"),
                 testGateway.events);
  }

  @Test
  public void testAlreadyExistsExecute() {
    ExistingDivisionGatewaySpy testGateway = new ExistingDivisionGatewaySpy();
    interactor = new DivisionInteractor(testGateway);
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.resourceExists(), response);
    assertEquals(List.of("has division: " + A_DIVISION), testGateway.events);
  }
}
