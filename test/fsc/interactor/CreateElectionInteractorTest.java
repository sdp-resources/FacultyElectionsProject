package fsc.interactor;

import fsc.mock.ElectionWithExistingSeatNameElectionGatewaySpy;
import fsc.mock.ElectingWithEverythingCorrectGatewaySpy;
import fsc.request.CreateElectionRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Ignore;
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

    ElectingWithEverythingCorrectGatewaySpy gateway =
          new ElectingWithEverythingCorrectGatewaySpy(request);
    interactor = new CreateElectionInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("Cool committee", gateway.submittedCommitteeName);
    assertTrue(response instanceof SuccessfullyCreatedElectionResponse);
    assertEquals(request.seatName, gateway.addedElection.getSeat().getName());
    assertTrue(gateway.addedElection.getCommittee().getName() == request.committeeName);
    assertTrue(gateway.addedElection.getID() == 1);
  }




}
