package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.*;
import fsc.request.AddToBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddToBallotInteractorTest {

  private String ballotID = "98705439870539870";
  private String profileUsername = "hayfieldj";

  @Test
  public void addingToNoBallot() {
    AddToBallotRequest request = new AddToBallotRequest(ballotID, profileUsername);

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
    AddToBallotRequest addToBallotRequest = new AddToBallotRequest(ballotID, profileUsername);

    BallotGateway dummyBallotGateway = new BallotGatewayDummy();
    ProfileGateway noProfileProfileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();

    AddToBallotInteractor interactor = new AddToBallotInteractor(dummyBallotGateway, noProfileProfileGateway);
    Response response = interactor.execute(addToBallotRequest);

    assertEquals("No profile with that username", ((ErrorResponse) response).response);
  }

  @Ignore
  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse()
  {

  }
}
