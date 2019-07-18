package fsc.interactor;

import fsc.entity.*;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.AddVoterRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableVoter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddViewRemoveVoterTest {

  private static final String USERNAME = "username";
  private static final String ELECTION_ID = "electionId";
  private static final long VOTER_ID = 3;
  private EntityFactory entityFactory = new SimpleEntityFactory();
  private ViewableEntityConverter converter = new ViewableEntityConverter();
  private AddVoterRequest request = new AddVoterRequest(USERNAME, ELECTION_ID);
  private Election election;
  private ElectionInteractor interactor;
  private Response response;
  private Profile profile;
  private Voter voter;

  @Before
  public void setUp() {
    election = EntityStub.simpleBallotElection();
    profile = EntityStub.getProfile(USERNAME);
    election.setID(ELECTION_ID);
    voter = new Voter(profile, election);
    voter.setVoterId(VOTER_ID);
  }

  @Test
  public void whenAddingAnExistingProfileToValidElection_returnTheAddedVoter() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
    response = interactor.execute(request);

    assertTrue(response.isSuccessful());
    assertTrue(electionGateway.hasSaved);
    assertNotNull(electionGateway.submittedVoter);
    ViewableVoter responseVoter = response.getValues();
    assertEquals(USERNAME, responseVoter.profile.getUsername());
    assertEquals(ELECTION_ID, responseVoter.election.electionID);
  }

  @Test
  public void whenAddingVoterWithNonexistingProfile_returnError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    InvalidProfileGatewaySpy profileGateway = new InvalidProfileGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);

    response = interactor.execute(request);
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
    response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertEquals(ELECTION_ID, electionGateway.requestedElectionId);
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenAddingVoterThatAlreadyExists_returnTheVoter() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    electionGateway.provideVoter(voter);
    interactor = new ElectionInteractor(electionGateway, null,
                                        new ExistingProfileGatewaySpy(profile),
                                        entityFactory);
    response = interactor.execute(request);

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
    response = interactor.execute(request);

    assertEquals(ResponseFactory.improperElectionState(), response);
    assertFalse(electionGateway.hasSaved);
  }

  // TODO: Add ability to remove voter
}
