package fsc.interactor.election;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.Voter;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.AddVoterRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.viewable.ViewableVoter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddViewRemoveVoterTest extends ElectionTest {

  private static final String USERNAME = "username";
  private static final long VOTER_ID = 3;
  private AddVoterRequest request;
  private Election election;
  private ElectionInteractor interactor;
  private Response<ViewableVoter> response;
  private Profile profile;
  private Voter voter;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
    profile = EntityStub.getProfile(USERNAME);
    voter = new Voter(profile, election);
    voter.setVoterId(VOTER_ID);
    request = new AddVoterRequest(USERNAME, election.getID());
  }

  @Test
  public void whenAddingAnExistingProfileToValidElection_returnTheAddedVoter() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
    response = interactor.handle(request);

    assertTrue(response.isSuccessful());
    assertTrue(electionGateway.hasSaved);
    assertNotNull(electionGateway.submittedVoter);
    ViewableVoter responseVoter = response.getValues();
    assertEquals(USERNAME, responseVoter.profile.getUsername());
    assertElectionIdEquals(responseVoter.electionId, election.getID());
  }

  @Test
  public void whenAddingVoterWithNonexistingProfile_returnError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    InvalidProfileGatewaySpy profileGateway = new InvalidProfileGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);

    response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownProfileName(), response);
    assertEquals(USERNAME, profileGateway.submittedUsername);
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenAddingVoterWithNonexistingElection_returnError() {
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        new ExistingProfileGatewaySpy(profile),
                                        entityFactory);
    response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertElectionIdEquals(electionGateway.requestedElectionId, election.getID());
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenAddingVoterThatAlreadyExists_returnTheVoter() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    electionGateway.provideVoter(voter);
    interactor = new ElectionInteractor(electionGateway, null,
                                        new ExistingProfileGatewaySpy(profile),
                                        entityFactory);
    response = interactor.handle(request);

    assertEquals(1, election.getVoters().size());
    assertEquals(ResponseFactory.voterExists(), response);
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenAddingAnExistingProfileWithInvalidElectionState_throwError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profile);
    election.setState(Election.State.DecideToStand);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    response = interactor.handle(request);

    assertEquals(ResponseFactory.improperElectionState(), response);
    assertFalse(electionGateway.hasSaved);
  }

  // TODO: Add ability to remove voter
}
