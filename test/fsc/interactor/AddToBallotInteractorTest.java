package fsc.interactor;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.AddToBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
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
  private AddToBallotInteractor interactor;
  private Profile profile;
  private ProfileGatewayStub profileGateway;

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
    interactor = new AddToBallotInteractor(new ProfileGatewayStub(),
                                           new RejectingElectionGatewaySpy());
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownElectionID(), response);
  }

  @Test
  public void addingNotRealProfile() {
    interactor = new AddToBallotInteractor(new InvalidProfileGatewaySpy(), electionGateway);
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse() {
    interactor = new AddToBallotInteractor(profileGateway, electionGateway);
    Response response = interactor.execute(request);

    assertEquals(new SuccessResponse(), response);
    assertThat(election.getBallot(), hasItem(profile));
    assertTrue(electionGateway.hasSaved);

  }
}
