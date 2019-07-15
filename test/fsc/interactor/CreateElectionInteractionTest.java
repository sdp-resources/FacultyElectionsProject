package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ProfileGateway;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.mock.gateway.election.AddedElectionGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.CreateElectionRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CreateElectionInteractionTest {

  public static final String ELECTION_ID = "an id";
  private CreateElectionRequest request;
  private ElectionInteractor interactor;
  private Response response;
  private ProfileGateway profileGateway = new ProfileGatewayStub();
  private AddedElectionGatewaySpy electionGateway;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    request = new CreateElectionRequest("President", "Cool committee");
  }

  @Test
  public void testCorrectElectionCreation() {
    electionGateway = new AddedElectionGatewaySpy(ELECTION_ID);
    AcceptingCommitteeGatewaySpy committeeGateway = new AcceptingCommitteeGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, committeeGateway, profileGateway, entityFactory);
    response = interactor.execute(request);
    assertEquals("Cool committee", committeeGateway.submittedCommitteeName);
    assertTrue(response.isSuccessful());
    assertEquals(request.seatName, electionGateway.addedElection.getSeat().getName());
    assertEquals(request.committeeName, electionGateway.addedElection.getSeat().getCommittee().getName());
    assertTrue(electionGateway.hasSaved);
    assertEquals(ELECTION_ID, response.getValues());
    assertEquals(Election.State.Setup, electionGateway.addedElection.getState());
  }

  @Test
  public void whenCommitteeNameIsMissingThenReturnsErrorResponse() {
    electionGateway = new AddedElectionGatewaySpy("an id");
    RejectingCommitteeGatewaySpy committeeGateway = new RejectingCommitteeGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, committeeGateway, profileGateway, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownCommitteeName(), response);
    assertNull(electionGateway.addedElection);
    assertNotNull(committeeGateway.submittedCommitteeName);
  }

}
