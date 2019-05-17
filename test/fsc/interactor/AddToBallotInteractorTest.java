package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.*;
import fsc.request.AddToBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyAddedProfileToBallotResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddToBallotInteractorTest {

  private String ballotID = "98705439870539870";
  private String profileUsername = "hayfieldj";
  private AddToBallotRequest request;

  @Before
  public void setUp() throws Exception {
    request = new AddToBallotRequest(ballotID, profileUsername);
  }

  @Test
  public void addingToNoBallot() {

    NoBallotExistsBallotGatewayStub noBallotBallotGateway = new NoBallotExistsBallotGatewayStub();
    ProfileGateway dummyProfileGateway = new ProfileGatewayDummy();

    AddToBallotInteractor inter = new AddToBallotInteractor(noBallotBallotGateway,
                                                            dummyProfileGateway);
    Response response = inter.execute(request);

    assertEquals( "No ballot with that ID", ((ErrorResponse) response).response);
  }

  @Test
  public void addingNotRealProfile()
  {
    BallotGateway dummyBallotGateway = new BallotGatewayDummy();
    ProfileGateway noProfileProfileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();

    AddToBallotInteractor interactor = new AddToBallotInteractor(dummyBallotGateway, noProfileProfileGateway);
    Response response = interactor.execute(request);

    assertEquals("No profile with that username", ((ErrorResponse) response).response);
  }

  @Test
  public void ballotGatewaySaveFailedReturnsErrorResponse()
  {
    BallotGateway ballotGateway = new AlwaysFailsSaveBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub();

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertEquals("Ballot save failed", ((ErrorResponse) response).response);
  }

  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse()
  {
    BallotGateway ballotGateway = new GetEmptyBallotBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub();

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessfullyAddedProfileToBallotResponse);
  }

  @Test
  public void gatewayGetsBallotWithChanges()
  {
    var ballotGateway = new GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy();
    var profileGateway = new ProfileGatewayStub();

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertEquals(profileGateway.profile1, ballotGateway.SavedBallot.get(0));
  }
}
