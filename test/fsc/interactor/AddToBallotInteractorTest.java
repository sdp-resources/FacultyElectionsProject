package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.*;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.AddToBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddToBallotInteractorTest {

  private final String ballotID = "98705439870539870";
  private final String profileUsername = "hayfieldj";
  private AddToBallotRequest request;

  @Before
  public void setUp() {
    request = new AddToBallotRequest(ballotID, profileUsername);
  }

  @Test
  public void addingToNoBallot() {
    AddToBallotInteractor inter = new AddToBallotInteractor(new NoBallotExistsBallotGatewayStub(),
                                                            new ProfileGatewayStub());
    Response response = inter.execute(request);

    assertEquals(ErrorResponse.unknownBallotID(), response);
  }

  @Test
  public void addingNotRealProfile() {
    AddToBallotInteractor interactor = new AddToBallotInteractor(new BallotGatewayDummy(),
                                                                 new InvalidProfileGatewaySpy());
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse() {
    BallotGateway ballotGateway = new GetEmptyBallotBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub(
          new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void gatewayGetsBallotWithChanges() {
    GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy ballotGateway = new GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy();
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(
          new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertEquals(profileGateway.getAProfile(), ballotGateway.SavedBallot.get(0));
  }
}
