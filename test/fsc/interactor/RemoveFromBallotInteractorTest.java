package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class RemoveFromBallotInteractorTest {

  private final String ballotID = "98705439870539870";
  private final String profileUsername = "hayfieldj";
  private RemoveFromBallotRequest request;
  private Election election;
  private Profile profile;
  private ProvidedElectionGatewaySpy electionGateway;
  private ElectionInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    request = new RemoveFromBallotRequest(ballotID, profileUsername);
    election = EntityStub.simpleBallotElection();
    profile = EntityStub.getProfile(0);
    electionGateway = new ProvidedElectionGatewaySpy(election);
  }

  @Test
  public void ballotDoesNotExist() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null,
                                        new ProfileGatewayStub(profile), entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void profileDoesNotExist() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        new InvalidProfileGatewaySpy(), entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void removeFromEmptyBallot() {
    ProfileGateway profileGateway = new ProfileGatewayStub(profile);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.invalidCandidate(), response);
  }

  @Test
  public void profileRemovedFromBallotGivesSuccesfullyRemovedResponse() {
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(profile);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    election.getBallot().add(entityFactory.createCandidate(profile, election.getBallot()));
    Response response = interactor.execute(request);

    assertFalse(election.hasCandidate(profile));
    assertEquals(ResponseFactory.success(), response);
    assertTrue(electionGateway.hasSaved);
  }
}
