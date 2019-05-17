package fsc.interactor;

import fsc.entity.*;
import fsc.mock.*;
import fsc.request.SeeElectionCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Before;
import org.junit.Test;

public class SeeElectionCandidatesInteractorTest {
  private Committee committee;
  private Ballot ballot;
  private Profile profile;
  private Seat seat;

  @Before
  public void setup() {
    ballot = new Ballot();
    committee = new Committee("CCCC", "XXXX");
    profile = new Profile("Haris Skiadas", "skiadas", "Natural Science", "Tenured");
    seat = new Seat();
  }

  @Test
  public void NoElectionCandidates() {
    ElectionStub election = new ElectionStub(ballot, committee);
    SeeElectionCandidatesRequest request = new SeeElectionCandidatesRequest(election);
    BallotGatewaySpy gateway = new BallotGatewaySpy();
    SeeElectionCandidatesInteractor interactor = new SeeElectionCandidatesInteractor(gateway);
    Response response = interactor.execute(request);

    assert(response instanceof ErrorResponse);
  }
}
