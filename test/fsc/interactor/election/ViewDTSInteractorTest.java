package fsc.interactor.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
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

  private ViewDTSRequest request;
  private Election election;
  private Profile profile;
  private ViewDTSInteractor interactor;
  private ProvidedElectionGatewaySpy gateway;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
    profile = EntityStub.getProfile(0);
    request = new ViewDTSRequest(profile.getUsername(), election.getID());
  }

  @Test
  public void whenUserIsCandidate_canGetTheirPreference() throws
                                                          ElectionGateway.NoProfileInBallotException {
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    Candidate candidate = election.getCandidateByUsername(profile.getUsername());
    candidate.setStatus(Candidate.Status.Accepted);
    gateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);
    assertElectionIdEquals(gateway.providedElectionId, election.getID());
    assertEquals(ResponseFactory.ofCandidate(candidate), response);
  }

  @Test
  public void whenUserIsNotCandidate_errorReturned() {
    gateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);
    assertElectionIdEquals(gateway.providedElectionId, election.getID());
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
