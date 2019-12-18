package fsc.interactor.election;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.State;
import fsc.entity.query.Query;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.request.EditBallotQueryRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class EditBallotQueryInteractorTest extends ElectionTest {

  private EditBallotQueryRequest request;
  private Profile providedProfile;
  private Election election;
  private ElectionInteractor interactor;

  @Before
  public void setUp() {
    providedProfile = EntityStub.getProfile(0);
    request = new EditBallotQueryRequest(556, Query.always());
    election = EntityStub.simpleElectionWithCandidates();
    election.setCandidateQuery(Query.never());
  }

  @Test
  public void whenGivenInvalidElectionId_returnErrorResponse() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null, null, null);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenElectionIsNotInSetupMode_ThrowError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
    election.setState(State.DecideToStand);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    election.setState(State.Vote);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    election.setState(State.Closed);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertFalse(electionGateway.hasSaved);
    assertEquals(Query.never(), election.getCandidateQuery());
  }

  @Test
  public void whenGivenValidElectionId_replaceBallotWithNewBallotFromQuery() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.success(), response);
    assertThat(election.getCandidateProfiles(), hasItem(providedProfile));
    assertEquals(request.query, election.getCandidateQuery());
    assertElectionIdEquals(electionGateway.providedElectionId, request.electionID);
    assertTrue(profileGateway.getProfilesWasCalled);
    assertTrue(electionGateway.hasSaved);
  }
}

