package fsc.interactor;

import fsc.entity.*;
import fsc.mock.*;
import fsc.request.ViewCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyViewedCandidatesResponse;
import org.junit.Before;
import org.junit.Test;
import fsc.viewable.ViewableProfile;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ViewCandidatesInteractorTest {
  private Ballot ballot;
  private String electionId;
  private List<ViewableProfile> testList = new ArrayList<>();
  private ViewableProfile profile1;
  private ViewableProfile profile2;
  private ViewableProfile profile3;
  private ViewableProfile profile4;

  @Before
  public void setup() {
    ballot = new Ballot();
    electionId = "mockID";
    profile1 = new ViewableProfile("Haris Skiadas", "skiadas", "Natural Science", "Tenured");
    profile2 = new ViewableProfile("Theresa Wilson", "wilson", "Natural Science", "tenure-track");
    profile3 = new ViewableProfile("Barb Wahl", "wahl", "Natural Science", "Tenured");
    profile4 = new ViewableProfile("John Collins", "collins", "Natural Science", "Tenured");
  }

  @Test
  public void canRecognizeEmptyBallot() throws Exception {
    ViewCandidatesRequest request = new ViewCandidatesRequest(electionId);
    EmptyBallotGatewaySpy gateway = new EmptyBallotGatewaySpy();
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(gateway);
    Response response = interactor.execute(request);

    assert(response instanceof ErrorResponse);
  }

  @Test
  public void canRecognizeNonEmptyBallot() throws Exception {
    ViewCandidatesRequest request = new ViewCandidatesRequest(electionId);
    BallotGatewaySpy gateway = new BallotGatewaySpy();
    ballot = gateway.getBallot(electionId);
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(gateway);
    Response response = interactor.execute(request);

    assert(response instanceof SuccessfullyViewedCandidatesResponse);
  }

  @Test
  public void gatewayCanGetABallot() throws Exception {
    BallotGatewaySpy gateway = new BallotGatewaySpy();
    ballot = gateway.getBallot(electionId);

    assertTrue(gateway.canGetBallot);
  }

  @Test
  public void canViewABallot() throws Exception {
    addProfilesToTestList();
    ViewCandidatesRequest request = new ViewCandidatesRequest(electionId);
    BallotGatewaySpy gateway = new BallotGatewaySpy();
    ballot = gateway.getBallot(electionId);
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(gateway);
    SuccessfullyViewedCandidatesResponse response = (SuccessfullyViewedCandidatesResponse) interactor.execute(request);

    assertEquals(testList, response.viewableProfiles);
  }

  private void addProfilesToTestList() {
    testList.add(profile1);
    testList.add(profile2);
    testList.add(profile3);
    testList.add(profile4);
  }
}
