package fsc.interactor.election;

import fsc.entity.Election;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.EditElectionStateRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditElectionStateRequestTest extends ElectionTest {

  private EditElectionStateRequest request;
  private ElectionInteractor interactor;
  private Response response;
  private Election election;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
  }

  @Test
  public void whenElectionIdIsInvalid_throwAnError() {
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    request = new EditElectionStateRequest(election.getID(), "invalid");
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertElectionIdEquals(electionGateway.requestedElectionId, election.getID());
  }

  @Test
  public void whenStateIsInvalid_throwAnError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    request = new EditElectionStateRequest(election.getID(), "invalid");
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.invalidElectionState(), response);
    assertElectionIdEquals(electionGateway.providedElectionId, election.getID());
  }

  @Test
  public void whenElectionExistsAndStateIsValid_transitionToNewState() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    Election.State state = Election.State.DecideToStand;
    String stateString = state.toString();
    request = new EditElectionStateRequest(election.getID(), stateString);
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.success(), response);
    assertElectionIdEquals(electionGateway.providedElectionId, election.getID());
    assertEquals(state, election.getState());
    assertEquals(true, electionGateway.hasSaved);

  }

}