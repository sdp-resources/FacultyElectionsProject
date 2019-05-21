package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.mock.AddDivisionWhereOneDoesNotAlreadyExistGatewaySpy;
import fsc.mock.DivisionWithThatNameAlreadyExistsGatewaySpy;
import fsc.request.AddDivisionRequest;
import fsc.response.FailedtoAddDivision;
import fsc.response.Response;
import fsc.response.SuccessfullyAddedDivision;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DivisionInteractorTest {
  public DivisionGateway testGateway;
  public AddDivisionRequest request;
  public DivisionInteractor interactor;

  @Before
  public void setup() {
    request = new AddDivisionRequest("ENG");
  }

  @Test
  public void testCorrectExecute() throws Exception {
    AddDivisionWhereOneDoesNotAlreadyExistGatewaySpy testGateway =
          new AddDivisionWhereOneDoesNotAlreadyExistGatewaySpy();
    interactor = new DivisionInteractor(testGateway);
    Response response = DivisionInteractor.execute(request);
    assertEquals("ENG", testGateway.submittedDivisionName);
    assertTrue(response instanceof SuccessfullyAddedDivision);
  }

  @Test
  public void testAlreadyExistsExecute() throws Exception {
    DivisionWithThatNameAlreadyExistsGatewaySpy testGateway =
          new DivisionWithThatNameAlreadyExistsGatewaySpy();
    interactor = new DivisionInteractor(testGateway);
    Response response = DivisionInteractor.execute(request);
    assertEquals("ENG", testGateway.submittedDivisionName);
    assertTrue(response instanceof FailedtoAddDivision);
  }
}
