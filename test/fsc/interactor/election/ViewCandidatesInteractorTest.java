package fsc.interactor.election;

import fsc.entity.*;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.ViewCandidatesRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ViewCandidatesInteractorTest extends ElectionTest {
  private Election election;
  private ElectionInteractor interactor;

  @Before
  public void setup() {
    election = EntityStub.simpleElectionWithCandidates(
          EntityStub.getProfile(0), EntityStub.getProfile(1),
          EntityStub.getProfile(2), EntityStub.getProfile(3));
  }

  @Test
  public void canRecognizeEmptyBallot() {
    ViewCandidatesRequest request = new ViewCandidatesRequest(election.getID());
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null, null, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void canViewABallot() {
    ViewCandidatesRequest request = new ViewCandidatesRequest(election.getID());
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(electionGateway, null, null, entityFactory);
    Response initialResponse = interactor.execute(request);

    assertElectionIdEquals(electionGateway.providedElectionId, election.getID());
    assertEquals(ResponseFactory.ofProfileList(election.getCandidateProfiles()),
                 initialResponse);
  }

}
