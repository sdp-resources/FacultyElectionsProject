package fsc.interactor;

import fsc.entity.BallotCreator;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.mock.gateway.election.AddedElectionGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.CreateElectionRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CreateElectionInteractorTest {

  private CreateElectionRequest request;
  private CreateElectionInteractor interactor;
  private Response response;
  private BallotCreator ballotCreator = new BallotCreator(new ProfileGatewayStub());
  private AddedElectionGatewaySpy electionGateway;

  @Before
  public void setup() {
    request = new CreateElectionRequest("President", "Cool committee");
  }

  @Test
  public void testCorrectExecute() {
    electionGateway = new AddedElectionGatewaySpy();
    AcceptingCommitteeGatewaySpy committeeGateway = new AcceptingCommitteeGatewaySpy();
    interactor = new CreateElectionInteractor(electionGateway, committeeGateway, ballotCreator);
    response = interactor.execute(request);
    assertEquals("Cool committee", committeeGateway.submittedCommitteeName);
    assertEquals(new SuccessResponse(), response);
    assertEquals(request.seatName, electionGateway.addedElection.getSeat().getName());
    assertEquals(electionGateway.addedElection.getCommittee().getName(), request.committeeName);
    assertTrue(electionGateway.hasSaved);
  }

  @Test
  public void whenCommitteeNameIsMissingThenReturnsErrorResponse() {
    electionGateway = new AddedElectionGatewaySpy();
    RejectingCommitteeGatewaySpy committeeGateway = new RejectingCommitteeGatewaySpy();
    interactor = new CreateElectionInteractor(electionGateway, committeeGateway, ballotCreator);
    response = interactor.execute(request);
    assertEquals(ErrorResponse.unknownCommitteeName(), response);
    assertNull(electionGateway.addedElection);
    assertNotNull(committeeGateway.submittedCommitteeName);
  }

}
