package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.mock.ElectionGatewaySpy;
import fsc.mock.ProfileGatewayStub;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommiteeGatewaySpy;
import fsc.request.CreateElectionRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class CreateElectionInteractorTest {

  private CreateElectionRequest request;
  private CreateElectionInteractor interactor;
  private Response response;
  private final ProfileGateway profileGateway = new ProfileGatewayStub();

  @Before
  public void setup() {
    request = new CreateElectionRequest("President", "Cool committee");
  }

  @Test
  public void testCorrectExecute() {

    ElectionGatewaySpy electionGateway =
          new ElectionGatewaySpy();
    AcceptingCommitteeGatewaySpy committeeGateway = new AcceptingCommitteeGatewaySpy();
    interactor = new CreateElectionInteractor(electionGateway, committeeGateway, profileGateway);
    response = interactor.execute(request);
    assertEquals("Cool committee", committeeGateway.committeeNameRequested);
    assertTrue(response instanceof SuccessfullyCreatedElectionResponse);
    assertEquals(request.seatName, electionGateway.addedElection.getSeat().getName());
    assertEquals(electionGateway.addedElection.getCommittee().getName(), request.committeeName);
    assertEquals(1, electionGateway.addedElection.getID());
    assertTrue(electionGateway.hasSaved);
  }

  @Test
  public void whenCommitteeNameIsMissingThenReturnsErrorResponse() {
    ElectionGatewaySpy electionGateway = new ElectionGatewaySpy();
    RejectingCommiteeGatewaySpy committeeGateway = new RejectingCommiteeGatewaySpy();
    interactor = new CreateElectionInteractor(electionGateway, committeeGateway, profileGateway);
    response = interactor.execute(request);
    assertEquals(ErrorResponse.unknownCommitteeName(), response);
    assertNull(electionGateway.addedElection);
    assertNotNull(committeeGateway.committeeNameRequested);
  }

}
