package fsc.interactor.election;

import fsc.entity.Election;
import fsc.entity.SimpleEntityFactory;
import fsc.interactor.ElectionInteractor;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.EditElectionStateRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditElectionStateRequestTest extends ElectionTest {

  public static final long ELECTION_ID = 1;
  private EditElectionStateRequest request;
  private SimpleEntityFactory entityFactory = new SimpleEntityFactory();
  private ElectionInteractor interactor;
  private Response response;
  private Election election;

  @Before
  public void setUp() {
    election = new Election();
    election.setID(ELECTION_ID);
  }

  @Test
  public void whenElectionIdIsInvalid_throwAnError() {
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    request = new EditElectionStateRequest(ELECTION_ID, "invalid");
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertElectionIdEquals(electionGateway.requestedElectionId, ELECTION_ID);
  }

  @Test
  public void whenStateIsInvalid_throwAnError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    request = new EditElectionStateRequest(ELECTION_ID, "invalid");
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.invalidElectionState(), response);
    assertElectionIdEquals(electionGateway.providedElectionId, ELECTION_ID);
  }

  @Test
  public void whenElectionExistsAndStateIsValid_transitionToNewState() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    Election.State state = Election.State.DecideToStand;
    String stateString = state.toString();
    request = new EditElectionStateRequest(ELECTION_ID, stateString);
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.success(), response);
    assertElectionIdEquals(electionGateway.providedElectionId, ELECTION_ID);
    assertEquals(state, election.getState());
    assertEquals(true, electionGateway.hasSaved);

  }

}