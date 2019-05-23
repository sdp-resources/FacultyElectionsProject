package fsc.interactor;

import fsc.mock.ElectionGatewaySpy;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommiteeGatewaySpy;
import fsc.request.CreateElectionRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class CreateElectionInteractorTest {

  CreateElectionRequest request;
  private CreateElectionInteractor interactor;
  private Response response;

  @Before
  public void setup() {
    request = new CreateElectionRequest("President", "Cool committee");
  }

  @Test
  public void testCorrectExecute() {

    ElectionGatewaySpy electionGateway =
          new ElectionGatewaySpy();
    AcceptingCommitteeGatewaySpy committeeGateway = new AcceptingCommitteeGatewaySpy();
    interactor = new CreateElectionInteractor(electionGateway, committeeGateway);
    response = interactor.execute(request);
    assertEquals("Cool committee", committeeGateway.committeeNameRequested);
    assertTrue(response instanceof SuccessfullyCreatedElectionResponse);
    assertEquals(request.seatName, electionGateway.addedElection.getSeat().getName());
    assertTrue(electionGateway.addedElection.getCommittee().getName().equals(request.committeeName));
    assertTrue(electionGateway.addedElection.getID() == 1);
    assertTrue(electionGateway.hasSaved);
  }

  @Test
  public void whenCommitteeNameIsMissingThenReturnsErrorResponse() {
    ElectionGatewaySpy electionGateway = new ElectionGatewaySpy();
    RejectingCommiteeGatewaySpy committeeGateway = new RejectingCommiteeGatewaySpy();
    interactor = new CreateElectionInteractor(electionGateway, committeeGateway);
    response = interactor.execute(request);
    assertEquals(ErrorResponse.invalidCommitteeName(), response);
    assertNull(electionGateway.addedElection);
    assertNotNull(committeeGateway.committeeNameRequested);
  }

}
