package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.gateway.election.*;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class RemoveFromBallotInteractorTest {

  private final String ballotID = "98705439870539870";
  private final String profileUsername = "hayfieldj";
  private RemoveFromBallotRequest request;

  @Before
  public void setUp() {
    request = new RemoveFromBallotRequest(ballotID, profileUsername);
  }

  @Test
  public void ballotDoesNotExist() {

    NoBallotExistsBallotGatewayStub noBallotBallotGateway = new NoBallotExistsBallotGatewayStub();
    ProfileGateway dummyProfileGateway = new ProfileGatewayStub();

    RemoveFromBallotInteractor interactor = new RemoveFromBallotInteractor(noBallotBallotGateway,
                                                                           dummyProfileGateway);
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownBallotID(), response);
  }

  @Test
  public void profileDoesNotExist() {

    BallotGateway dummyBallotGateway = new BallotGatewayDummy();

    RemoveFromBallotInteractor interactor = new RemoveFromBallotInteractor(dummyBallotGateway,
                                                                           new InvalidProfileGatewaySpy());
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void removeFromEmptyBallot() {
    BallotGateway ballotGateway = new GetEmptyBallotBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub(
          new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));

    RemoveFromBallotInteractor interactor = new RemoveFromBallotInteractor(ballotGateway,
                                                                           profileGateway);
    Response response = interactor.execute(request);

    assertEquals("Ballot does not contain profile", ((ErrorResponse) response).message);
  }

  @Test
  public void profileRemovedFromBallotGivesSuccesfullyRemovedResponse() {
    Profile profile = new Profile("Banana", "Apple", "Art", "Tenured");
    BallotWithProfileStub ballotGateway = new BallotWithProfileStub(profile);
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(profile);
    RemoveFromBallotInteractor interactor = new RemoveFromBallotInteractor(ballotGateway,
                                                                           profileGateway);
    Response response = interactor.execute(request);

    assertFalse(ballotGateway.ballot.contains(profile));
    assertEquals(new SuccessResponse(), response);
  }
}
