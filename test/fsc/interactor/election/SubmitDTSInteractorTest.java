package fsc.interactor.election;

import fsc.entity.*;
import fsc.interactor.SubmitDTSInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.DTSRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static fsc.entity.Candidate.Status;
import static org.junit.Assert.*;

public class SubmitDTSInteractorTest extends ElectionTest {

  private long electionID = 12345;
  private Status status = Status.Declined;
  private Election election;

  private ProvidedElectionGatewaySpy electionGatewaySpy;
  private DTSRequest request;
  private SubmitDTSInteractor interactor;
  private Profile profile;
  private EntityFactory entityFactory = new SimpleEntityFactory();

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
    election.getBallot().add(entityFactory.createCandidate(profile, election.getBallot()));
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.success(), response);
    assertElectionIdEquals(electionGatewaySpy.providedElectionId, electionID);
    assertEquals(status, election.getCandidateByUsername(profile.getUsername()).getStatus());
    assertTrue(electionGatewaySpy.hasSaved);
  }

  @Test
  public void whenCandidateIsNotInElection_thenError() {
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.invalidCandidate(), response);
    assertFalse(electionGatewaySpy.hasSaved);
  }

  @Test
  public void whenElectionDoesNotExist_thenError() {
    RejectingElectionGatewaySpy rejectingGateway = new RejectingElectionGatewaySpy();
    interactor = new SubmitDTSInteractor(rejectingGateway);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }
}

