package fsc.interactor.election;

import fsc.entity.Candidate;
import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.gateway.ElectionGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.ViewDTSRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewDTSInteractorTest extends ElectionTest {

  private ViewDTSRequest request;
  private Election election;
  private Profile profile;
  private ElectionInteractor interactor;
  private ProvidedElectionGatewaySpy gateway;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
    profile = EntityStub.getProfile(0);
    request = new ViewDTSRequest(profile.getUsername(), election.getID());
  }

  @Test
  public void whenUserIsCandidate_canGetTheirPreference()
        throws ElectionGateway.NoProfileInBallotException {
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    election.setState(Election.State.DecideToStand);
    Candidate candidate = election.getCandidateByUsername(profile.getUsername());
    candidate.setStatus(Candidate.Status.Accepted);
    gateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(gateway, null, null, null);
    Response response = interactor.handle(request);
    assertElectionIdEquals(gateway.providedElectionId, election.getID());
    assertEquals(ResponseFactory.ofCandidate(candidate), response);
  }

  @Test
  public void whenUserIsNotCandidate_errorReturned() {
    election.setState(Election.State.DecideToStand);
    gateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(gateway, null, null, null);
    Response response = interactor.handle(request);
    assertElectionIdEquals(gateway.providedElectionId, election.getID());
    assertEquals(ResponseFactory.invalidCandidate(), response);
  }

  @Test
  public void whenElectionDoesNotExist_thenError() {
    RejectingElectionGatewaySpy rejectingGateway = new RejectingElectionGatewaySpy();
    interactor = new ElectionInteractor(rejectingGateway, null, null, null);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void shouldNotBeAbleToViewStatusIfOnSetupPhase()
        throws ElectionGateway.NoProfileInBallotException {
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    Candidate candidate = election.getCandidateByUsername(profile.getUsername());
    candidate.setStatus(Candidate.Status.Accepted);
    gateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(gateway, null, null, null);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertEquals(false, gateway.hasSaved);
  }
}
