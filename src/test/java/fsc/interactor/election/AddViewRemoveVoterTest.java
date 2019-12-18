package fsc.interactor.election;

import fsc.entity.*;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.AddVoterRequest;
import fsc.request.RemoveVoterRequest;
import fsc.request.Request;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.viewable.ViewableVoter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddViewRemoveVoterTest extends ElectionTest {

  private static final String USERNAME = "username";
  private static final long VOTER_ID = 3;
  private AddVoterRequest addRequest;
  private Election election;
  private ElectionInteractor interactor;
  private Response<ViewableVoter> response;
  private Profile profile;
  private Voter voter;
  private Request removeRequest;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
    profile = EntityStub.getProfile(USERNAME);
    voter = new Voter(profile, election);
    voter.setVoterId(VOTER_ID);
    addRequest = new AddVoterRequest(election.getID(), USERNAME);
    removeRequest = new RemoveVoterRequest(election.getID(), USERNAME);
  }

  @Test
  public void whenAddingAnExistingProfileToValidElection_returnTheAddedVoter() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
    response = interactor.handle(addRequest);

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

    response = interactor.handle(addRequest);
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
    response = interactor.handle(addRequest);

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
    response = interactor.handle(addRequest);

    assertEquals(1, election.getVoters().size());
    assertEquals(ResponseFactory.voterExists(), response);
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenAddingAnExistingProfileWithInvalidElectionState_throwError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profile);
    election.setState(State.Closed);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    response = interactor.handle(addRequest);

    assertEquals(ResponseFactory.improperElectionState(), response);
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenRemovingVoterWithNonexistingProfile_returnError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    InvalidProfileGatewaySpy profileGateway = new InvalidProfileGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);

    response = interactor.handle(removeRequest);
    assertEquals(ResponseFactory.unknownProfileName(), response);
    assertEquals(USERNAME, profileGateway.submittedUsername);
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenRemovingVoterFromNonexistingElection_returnError() {
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        new ExistingProfileGatewaySpy(profile),
                                        entityFactory);
    response = interactor.handle(removeRequest);

    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertElectionIdEquals(electionGateway.requestedElectionId, election.getID());
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenRemovingAnExistingVoterFromValidElection_returnTheVoter() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
    election.addVoter(entityFactory.createVoter(profile, election));
    response = interactor.handle(removeRequest);

    assertTrue(response.isSuccessful());
    assertTrue(electionGateway.hasSaved);
    ViewableVoter responseVoter = response.getValues();
    assertEquals(USERNAME, responseVoter.profile.getUsername());
  }

  @Test
  public void whenRemovingVoterThatDoesNotExists_error() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(electionGateway, null,
                                        new ExistingProfileGatewaySpy(profile),
                                        entityFactory);
    response = interactor.handle(removeRequest);

    assertEquals(ResponseFactory.voterMissing(), response);
    assertFalse(electionGateway.hasSaved);
  }

  @Test
  public void whenRemovingAnExistingVoterFromInvalidElectionState_throwError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profile);
    election.setState(State.Closed);
    election.addVoter(entityFactory.createVoter(profile, election));
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    response = interactor.handle(removeRequest);

    assertEquals(ResponseFactory.improperElectionState(), response);
    assertFalse(electionGateway.hasSaved);
  }

}
