package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Election;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.ViewCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ViewCandidatesInteractorTest {
  public static final String MOCK_ID = "mockID";
  private Ballot ballot;
  private Election election;

  private static Ballot sampleBallot() {
    Ballot aBallot = new Ballot();
    aBallot.add(EntityStub.getProfile(3));
    aBallot.add(EntityStub.getProfile(2));
    aBallot.add(EntityStub.getProfile(1));
    aBallot.add(EntityStub.getProfile(0));
    return aBallot;
  }

  @Before
  public void setup() {
    ballot = sampleBallot();
    election = EntityStub.simpleBallotElection(ballot);
  }

  @Test
  public void canRecognizeEmptyBallot() {
    ViewCandidatesRequest request = new ViewCandidatesRequest(MOCK_ID);
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(electionGateway);
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownElectionID(), response);
  }

  @Test
  public void canViewABallot() {
    ViewCandidatesRequest request = new ViewCandidatesRequest(MOCK_ID);
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(electionGateway);
    Response initialResponse = interactor.execute(request);

    assertEquals(MOCK_ID, electionGateway.providedElectionId);
    assertEquals(ViewResponse.ofProfileList(ballot), initialResponse);
  }

}
