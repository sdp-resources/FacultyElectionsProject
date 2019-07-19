package fsc.interactor.election;

import fsc.entity.*;
import fsc.interactor.ViewDTSInteractor;
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

  private final long ELECTION_ID = 1;
  private ViewDTSRequest request;
  private Election election;
  private Profile profile;
  private ViewDTSInteractor interactor;
  private ProvidedElectionGatewaySpy gateway;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    election = EntityStub.simpleBallotElection();
    profile = EntityStub.getProfile(0);
    request = new ViewDTSRequest(profile.getUsername(), ELECTION_ID);
  }

  @Test
  public void whenUserIsCandidate_canGetTheirPreference() throws Ballot.NoProfileInBallotException {
    election.getBallot().add(entityFactory.createCandidate(profile, election.getBallot()));
    Candidate candidate = election.getCandidateByUsername(profile.getUsername());
    candidate.setStatus(Candidate.Status.Accepted);
    gateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);
    assertElectionIdEquals(gateway.providedElectionId, ELECTION_ID);
    assertEquals(ResponseFactory.ofCandidate(candidate), response);
  }

  @Test
  public void whenUserIsNotCandidate_errorReturned() {
    gateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);
    assertElectionIdEquals(gateway.providedElectionId, ELECTION_ID);
    assertEquals(ResponseFactory.invalidCandidate(), response);
  }

  @Test
  public void whenElectionDoesNotExist_thenError() {
    RejectingElectionGatewaySpy rejectingGateway = new RejectingElectionGatewaySpy();
    interactor = new ViewDTSInteractor(rejectingGateway);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }
}
