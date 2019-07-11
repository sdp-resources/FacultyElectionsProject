package fsc.interactor;

import fsc.entity.*;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.request.ViewCandidatesRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ViewCandidatesInteractorTest {
  public static final String MOCK_ID = "mockID";
  private Ballot ballot;
  private Election election;
  private ElectionInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  private Ballot sampleBallot() {
    Ballot aBallot = entityFactory.createBallot();
    aBallot.add(entityFactory.createCandidate(EntityStub.getProfile(3), aBallot));
    aBallot.add(entityFactory.createCandidate(EntityStub.getProfile(2), aBallot));
    aBallot.add(entityFactory.createCandidate(EntityStub.getProfile(1), aBallot));
    aBallot.add(entityFactory.createCandidate(EntityStub.getProfile(0), aBallot));
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
    interactor = new ElectionInteractor(electionGateway, null, null, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void canViewABallot() {
    ViewCandidatesRequest request = new ViewCandidatesRequest(MOCK_ID);
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    interactor = new ElectionInteractor(electionGateway, null, null, entityFactory);
    Response initialResponse = interactor.execute(request);

    assertEquals(MOCK_ID, electionGateway.providedElectionId);
    assertEquals(ResponseFactory.ofProfileList(ballot.getCandidateProfiles()), initialResponse);
  }

}
