package fsc.interactor.election;

import fsc.entity.*;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.ViewVoterRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewVoterTest extends ElectionTest {

  private ViewVoterRequest request;
  private ElectionInteractor interactor;
  private Response response;
  private Election election;
  private Committee committee = null;
  private Profile[] profiles;
  private Voter voter;

  @Before
  public void setUp() {
    profiles = new Profile[]{ EntityStub.getProfile(1), EntityStub.getProfile(2) };
    election = EntityStub.simpleElectionWithCandidates(profiles[0]);
    voter = new Voter(profiles[0], election);
    election.addVoter(voter);
    election.setState(State.Vote);
    request = new ViewVoterRequest(election.getID(), profiles[0].getUsername());
  }

  @Test
  public void whenElectionIdIsInvalid_throwAnError() {
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profiles);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertElectionIdEquals(electionGateway.requestedElectionId, election.getID());
  }

  @Test
  public void whenProfileDoesNotExist_returnError() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    InvalidProfileGatewaySpy profileGateway = new InvalidProfileGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void whenVoterDoesNotExist_returnError() {
    election.removeVoter(voter);
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(profiles);
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidVoter(), response);
  }

  @Test
  public void whenVoterIsValid_returnVoterInformation() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ProvidedCommitteeGatewaySpy committeeGateway = new ProvidedCommitteeGatewaySpy(committee);
    ProfileGateway profileGateway = new ExistingProfileGatewaySpy(profiles);
    interactor = new ElectionInteractor(electionGateway, committeeGateway,
                                        profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.ofVoter(voter), response);
    assertEquals(false, electionGateway.hasSaved);
  }
}