package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
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

public class EditBallotQueryInteractorTest {

  private EditBallotQueryRequest request;
  private Profile providedProfile;
  private Election election;
  private ElectionInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    providedProfile = EntityStub.getProfile(0);
    request = new EditBallotQueryRequest("556", Query.always());
    election = EntityStub.simpleBallotElection();
  }

  @Test
  public void whenGivenInvalidElectionId_returnErrorResponse() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null, null, null);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenGivenValidElectionId_replaceBallotWithNewBallotFromQuery() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.success(), response);
    assertThat(election.getCandidateProfiles(), hasItem(providedProfile));
    assertEquals(request.query, election.getCandidateQuery());
    assertEquals(request.electionID, electionGateway.providedElectionId);
    assertTrue(profileGateway.getAllProfilesWasCalled);
    assertTrue(electionGateway.hasSaved);
  }
}

