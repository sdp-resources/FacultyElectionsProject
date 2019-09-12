package fsc.interactor.election;

import fsc.entity.Election;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.mock.gateway.election.AddedElectionGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.CreateElectionRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.viewable.ViewableElection;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CreateElectionInteractionTest extends ElectionTest {

  public static final long ELECTION_ID = 6;
  private CreateElectionRequest request;
  private ElectionInteractor interactor;
  private Response<ViewableElection> response;
  private ProfileGateway profileGateway = new ProfileGatewayStub();
  private AddedElectionGatewaySpy electionGateway;

  // TODO: election creation request should use seat id?
  @Before
  public void setup() {
    request = new CreateElectionRequest("President", "Cool committee");
  }

  @Test
  public void testCorrectElectionCreation() {
    electionGateway = new AddedElectionGatewaySpy(ELECTION_ID);
    AcceptingCommitteeGatewaySpy committeeGateway = new AcceptingCommitteeGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, committeeGateway, profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals("Cool committee", committeeGateway.submittedCommitteeName);
    assertTrue(response.isSuccessful());
    assertEquals(request.seatName, electionGateway.addedElection.getSeat().getName());
    assertEquals(request.committeeName, electionGateway.addedElection.getSeat().getCommittee().getName());
    assertTrue(electionGateway.hasSaved);
    ViewableElection createdElection = response.getValues();
    assertElectionIdEquals(createdElection.electionID, ELECTION_ID);
    assertEquals(Election.State.Setup, electionGateway.addedElection.getState());
  }

  @Test
  public void whenCommitteeNameIsMissingThenReturnsErrorResponse() {
    electionGateway = new AddedElectionGatewaySpy(2);
    RejectingCommitteeGatewaySpy committeeGateway = new RejectingCommitteeGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, committeeGateway, profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownCommitteeName(), response);
    assertNull(electionGateway.addedElection);
    assertNotNull(committeeGateway.submittedCommitteeName);
  }

}
