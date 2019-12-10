package fsc.interactor.election;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.AddToBallotRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class AddToBallotInteractorTest extends ElectionTest {

  private final String profileUsername = "hayfieldj";
  private AddToBallotRequest request;
  private Election election;
  private ProvidedElectionGatewaySpy electionGateway;
  private ElectionInteractor interactor;
  private Profile profile;
  private ProfileGatewayStub profileGateway;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
    request = new AddToBallotRequest(election.getID(), profileUsername);
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profile = EntityStub.getProfile(0);
    profileGateway = new ProfileGatewayStub(profile);
  }

  @Test
  public void addingToNoBallot() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null,
                                        new ProfileGatewayStub(profile), entityFactory);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void addingNotRealProfile() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        new InvalidProfileGatewaySpy(), entityFactory);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.success(), response);
    assertThat(election.getCandidateProfiles(), hasItem(profile));
    assertTrue(electionGateway.hasSaved);
  }

  @Test
  public void addingToBallotNotInSetupModeGivesErrors() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    election.setState(Election.State.Vote);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    election.setState(Election.State.DecideToStand);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    election.setState(Election.State.Closed);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertThat(election.getCandidateProfiles(), not(hasItem(profile)));
    assertFalse(electionGateway.hasSaved);
  }
}
