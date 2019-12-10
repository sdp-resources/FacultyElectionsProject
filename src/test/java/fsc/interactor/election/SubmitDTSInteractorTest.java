package fsc.interactor.election;

import fsc.entity.Candidate;
import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.gateway.ElectionGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.SetDTSRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static fsc.entity.Candidate.Status;
import static org.junit.Assert.*;

public class SubmitDTSInteractorTest extends ElectionTest {

  private long electionID = 12345;
  private String status = Status.Declined.toString();
  private Election election;

  private ProvidedElectionGatewaySpy electionGatewaySpy;
  private SetDTSRequest request;
  private ElectionInteractor interactor;
  private Profile profile;

  @Before
  public void setUp() {
    election = EntityStub.simpleElectionWithCandidates();
    election.setState(Election.State.DecideToStand);
    profile = EntityStub.getProfile(0);
    request = new SetDTSRequest(electionID, profile.getUsername(), status);
    electionGatewaySpy = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(electionGatewaySpy, null, null, null);
  }

  @Test
  public void whenCandidateIsInElection_thenRecordTheirStatus()
        throws ElectionGateway.NoProfileInBallotException {
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.success(), response);
    assertElectionIdEquals(electionGatewaySpy.providedElectionId, electionID);
    assertEquals(Status.valueOf(status),
                 election.getCandidateByUsername(profile.getUsername()).getStatus());
    assertTrue(electionGatewaySpy.hasSaved);
  }

  @Test
  public void whenCandidateIsNotInElection_thenError() {
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.invalidCandidate(), response);
    assertFalse(electionGatewaySpy.hasSaved);
  }

  @Test
  public void whenAssignedStatusIsNotValidValue_thenError() {
    election.getCandidates().add(entityFactory.createCandidate(profile, election));
    request = new SetDTSRequest(electionID, profile.getUsername(), "bogus");
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.invalidCandidateStatus(), response);
    assertFalse(electionGatewaySpy.hasSaved);
  }

  @Test
  public void whenElectionDoesNotExist_thenError() {
    RejectingElectionGatewaySpy rejectingGateway = new RejectingElectionGatewaySpy();
    interactor = new ElectionInteractor(rejectingGateway, null, null, null);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenElectionNotInDTSState_thenError() {
    election.setState(Election.State.Setup);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    election.setState(Election.State.Vote);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    election.setState(Election.State.Closed);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertFalse(electionGatewaySpy.hasSaved);
    for (Candidate candidate : election.getCandidates()) {
      assertEquals(Status.NoAnswer, candidate.getStatus());
    }

  }
}

