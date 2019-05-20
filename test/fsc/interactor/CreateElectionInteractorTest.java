package fsc.interactor;

import fsc.gateway.ElectionGateway;
import fsc.mock.ElectionWithExistingSeatNameElectionGatewaySpy;
import fsc.mock.NoElectionWithThatSeatNameElectionGatewaySpy;
import fsc.request.CreateElectionRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CreateElectionInteractorTest {

  CreateElectionRequest request;
  private CreateElectionInteractor interactor;
  private CreateElectionResponse response;

  @Before
  public void setup() {
    request = new CreateElectionRequest("President", "Cool committee");
  }

  @Test
  public void testCorrectExecute() throws Exception {
    NoElectionWithThatSeatNameElectionGatewaySpy gateway =
          new NoElectionWithThatSeatNameElectionGatewaySpy();
    interactor = new CreateElectionInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("President", gateway.submittedSeatName);
    assertTrue(response instanceof SuccessfullyCreatedElectionResponse);
  }

  @Test
  public void alreadyUsedSeatName() throws Exception {
    ElectionWithExistingSeatNameElectionGatewaySpy gateway =
          new ElectionWithExistingSeatNameElectionGatewaySpy();
    interactor = new CreateElectionInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(response instanceof FailedToCreateElectionResponse);
  }
}
