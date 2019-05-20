package fsc.interactor;

import fsc.entity.*;
import fsc.mock.*;
import fsc.request.ViewCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyViewedCandidates;
import org.junit.Before;
import org.junit.Test;

public class ViewCandidatesInteractorTest {
  private Committee committee;
  private Ballot ballot;
  private Profile profile;
  private Election election;
  private String electionId;

  @Before
  public void setup() {
    ballot = new Ballot();
    committee = new Committee("CCCC", "XXXX");
    profile = new Profile("Haris Skiadas", "skiadas", "Natural Science", "Tenured");
    election = new Election();
    electionId = "mockID";
  }

  @Test
  public void NoElectionCandidates() {
    ViewCandidatesRequest request = new ViewCandidatesRequest(electionId);
    BallotGatewaySpy gateway = new BallotGatewaySpy();
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(gateway);
    Response response = interactor.execute(request);

    assert(response instanceof ErrorResponse);
  }

  @Test
  public void GetsCorrectElectionCandidates() throws Exception {
    ViewCandidatesRequest request = new ViewCandidatesRequest(electionId);
    BallotGatewaySpy gateway = new BallotGatewaySpy();
    ballot = gateway.getBallot(electionId);
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(gateway);
    Response response = interactor.execute(request);

    assert(response instanceof SuccessfullyViewedCandidates);
  }
}
