package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Election;
import fsc.entity.Profile;
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
    aBallot.add(new Profile("Haris Skiadas", "skiadas", "Natural Science", "Tenured"));
    aBallot.add(new Profile("Theresa Wilson", "wilson", "Natural Science", "tenure-track"));
    aBallot.add(new Profile("Barb Wahl", "wahl", "Natural Science", "Tenured"));
    aBallot.add(new Profile("John Collins", "collins", "Natural Science", "Tenured"));
    return aBallot;
  }

  @Before
  public void setup() {
    ballot = sampleBallot();
    election = new Election(null, null, null, ballot);
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
