package fsc.interactor;

import fsc.entity.*;
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
import static org.junit.Assert.*;

public class AddToBallotInteractorTest {

  private final String ELECTION_ID = "98705439870539870";
  private final String profileUsername = "hayfieldj";
  private AddToBallotRequest request;
  private Election election;
  private ProvidedElectionGatewaySpy electionGateway;
  private ElectionInteractor interactor;
  private Profile profile;
  private ProfileGatewayStub profileGateway;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    request = new AddToBallotRequest(ELECTION_ID, profileUsername);
    election = EntityStub.simpleBallotElection();
    election.setID(ELECTION_ID);
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profile = EntityStub.getProfile(0);
    profileGateway = new ProfileGatewayStub(profile);
  }

  @Test
  public void addingToNoBallot() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null,
                                        new ProfileGatewayStub(profile), entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void addingNotRealProfile() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        new InvalidProfileGatewaySpy(), entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.success(), response);
    assertThat(election.getCandidateProfiles(), hasItem(profile));
    assertTrue(electionGateway.hasSaved);

  }
}
