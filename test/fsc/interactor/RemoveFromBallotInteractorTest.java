package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.*;
import fsc.request.AddToBallotRequest;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RemoveFromBallotInteractorTest {

  private String ballotID = "98705439870539870";
  private String profileUsername = "hayfieldj";
  private RemoveFromBallotRequest request;

  @Before
  public void setUp() throws Exception {
    request = new RemoveFromBallotRequest(ballotID, profileUsername);
  }

  @Test
  public void ballotDoesNotExist(){

    NoBallotExistsBallotGatewayStub noBallotBallotGateway = new NoBallotExistsBallotGatewayStub();
    ProfileGateway dummyProfileGateway = new ProfileGatewayDummy();

    RemoveFromBallotInteractor inter = new RemoveFromBallotInteractor(noBallotBallotGateway,
                                                            dummyProfileGateway);
    Response response = inter.execute(request);

    assertEquals("No ballot with that ID", ((ErrorResponse)response).response);
  }

  @Test
  public void profileDoesNotExist(){

    BallotGateway dummyBallotGateway = new BallotGatewayDummy();
    ProfileGateway noProfileProfileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();

    RemoveFromBallotInteractor interactor = new RemoveFromBallotInteractor(dummyBallotGateway,
                                                             noProfileProfileGateway);
    Response response = interactor.execute(request);

    assertEquals("No profile with that username", ((ErrorResponse)response).response);
  }

  @Test
  public void removeFromEmptyBallot()
  {
    BallotGateway ballotGateway = new GetEmptyBallotBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub();

    RemoveFromBallotInteractor interactor = new RemoveFromBallotInteractor(ballotGateway,
                                                                           profileGateway);
    Response response = interactor.execute(request);

    assertEquals("Ballot does not contain profile", ((ErrorResponse)response).response);
  }

  @Test
  public void ballotGatewayRemoveSaveFailed(){

    BallotGateway ballotGateway = new AlwaysFailsSaveBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub();

    RemoveFromBallotInteractor interactor = new RemoveFromBallotInteractor(ballotGateway,
                                                                      profileGateway);
    Response response = interactor.execute(request);

    assertEquals("Ballot save failed", ((ErrorResponse) response).response);
  }
}
