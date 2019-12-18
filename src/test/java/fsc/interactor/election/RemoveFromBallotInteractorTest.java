package fsc.interactor.election;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.State;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RemoveFromBallotInteractorTest extends ElectionTest {

  private final String profileUsername = "hayfieldj";
  private RemoveFromBallotRequest request;
  private Election election;
  private Profile profile;
  private ProvidedElectionGatewaySpy electionGateway;
  private ElectionInteractor interactor;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
    request = new RemoveFromBallotRequest(election.getID(), profileUsername);
    profile = EntityStub.getProfile(0);
    electionGateway = new ProvidedElectionGatewaySpy(election);
  }

  @Test
  public void ballotDoesNotExist() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null,
                                        new ProfileGatewayStub(profile), entityFactory);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void profileDoesNotExist() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        new InvalidProfileGatewaySpy(), entityFactory);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void removeFromEmptyBallotDoesNotDoAnything() {
    ProfileGateway profileGateway = new ProfileGatewayStub(profile);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.success(), response);
  }

  @Test
  public void profileRemovedFromBallotGivesSuccesfullyRemovedResponse() {
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(profile);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    Response response = interactor.handle(request);

    assertFalse(election.hasCandidate(profile));
    assertEquals(ResponseFactory.success(), response);
    assertTrue(electionGateway.hasSaved);
  }

  @Test
  public void removingFromBallotNotInSetupModeGivesErrors() {
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(profile);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    election.setState(State.Vote);
    Assert.assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    election.setState(State.Closed);
    Assert.assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertThat(election.getCandidateProfiles(), hasItem(profile));
    Assert.assertFalse(electionGateway.hasSaved);
  }

}
