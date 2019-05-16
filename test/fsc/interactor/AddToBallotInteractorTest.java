package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.*;
import fsc.request.AddToBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyAddedProfileToBallotResponse;
import org.junit.Before;
import org.junit.Ignore;
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
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse()
  {
    BallotGateway ballotGateway = new GetBallotFromIDBallotGatewayStub();
    ProfileGateway profileGateway = new GetProfileFromUsernameProfileGatewayStub();

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessfullyAddedProfileToBallotResponse);
  }
}
