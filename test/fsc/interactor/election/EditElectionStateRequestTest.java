package fsc.interactor.election;

import fsc.entity.Candidate;
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

import static fsc.entity.Election.State.*;
import static org.junit.Assert.*;

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
    response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertElectionIdEquals(electionGateway.requestedElectionId, election.getID());
  }

  @Test
  public void whenStateIsInvalid_throwAnError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    request = new EditElectionStateRequest(election.getID(), "invalid");
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidElectionState(), response);
    assertElectionIdEquals(electionGateway.providedElectionId, election.getID());
  }

  @Test
  public void whenElectionExistsAndStateIsValid_transitionToNewState() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    Election.State state = DecideToStand;
    request = new EditElectionStateRequest(election.getID(), state.toString());
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.success(), response);
    assertElectionIdEquals(electionGateway.providedElectionId, election.getID());
    assertEquals(state, election.getState());
    assertEquals(true, electionGateway.hasSaved);
  }

  @Test
  public void ifNewStateIsVoteAutomaticallyAcceptForAllNonrespondedCandidates() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    election.setState(Setup);
    Candidate candidate1 = entityFactory.createCandidate(EntityStub.getProfile(1), election);
    election.addCandidate(candidate1);
    Candidate candidate2 = entityFactory.createCandidate(EntityStub.getProfile(2), election);
    election.addCandidate(candidate2);
    candidate2.setStatusDeclined();
    request = new EditElectionStateRequest(election.getID(), DecideToStand.toString());
    interactor.handle(request);
    assertFalse(candidate1.hasAccepted());
    assertTrue(candidate2.hasDeclined());
    request = new EditElectionStateRequest(election.getID(), Vote.toString());
    interactor.handle(request);
    assertTrue(candidate1.hasAccepted());
    assertTrue(candidate2.hasDeclined());
  }

}