package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static fsc.entity.Candidate.Status;
import static org.junit.Assert.*;

public class SubmitDTSInteractorTest {

  private String electionID = "12345";
  private Status status = Status.Declined;
  private Election election;

  private ProvidedElectionGatewaySpy electionGatewaySpy;
  private DTSRequest request;
  private SubmitDTSInteractor interactor;
  private Profile profile;

  @Before
  public void setUp() {
    election = EntityStub.simpleBallotElection();
    profile = EntityStub.getProfile(0);
    request = new DTSRequest(electionID, profile.getUsername(), status);
    electionGatewaySpy = new ProvidedElectionGatewaySpy(election);
    interactor = new SubmitDTSInteractor(electionGatewaySpy);
  }

  @Test
  public void whenCandidateIsInElection_thenRecordTheirStatus()
        throws Ballot.NoProfileInBallotException {
    election.getBallot().add(profile);
    Response response = interactor.execute(request);

    assertEquals(new SuccessResponse(), response);
    assertEquals(electionID, electionGatewaySpy.providedElectionId);
    assertEquals(status, election.getCandidateByUsername(profile.getUsername()).getStatus());
    assertTrue(electionGatewaySpy.hasSaved);
  }

  @Test
  public void whenCandidateIsNotInElection_thenError() {
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.invalidCandidate(), response);
    assertFalse(electionGatewaySpy.hasSaved);
  }

  @Test
  public void whenElectionDoesNotExist_thenError() {
    RejectingElectionGatewaySpy rejectingGateway = new RejectingElectionGatewaySpy();
    interactor = new SubmitDTSInteractor(rejectingGateway);
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownElectionID(), response);
  }
}

