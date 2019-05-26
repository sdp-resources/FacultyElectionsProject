package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.*;
import fsc.request.AddToBallotRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
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

    NoBallotExistsBallotGatewayStub noBallotBallotGateway = new NoBallotExistsBallotGatewayStub();
    ProfileGateway dummyProfileGateway = new ProfileGatewayDummy();

    AddToBallotInteractor inter = new AddToBallotInteractor(noBallotBallotGateway,
                                                            dummyProfileGateway);
    Response response = inter.execute(request);

    assertEquals( ErrorResponse.unknownBallotID(), response);
  }

  @Test
  public void addingNotRealProfile()
  {
    BallotGateway dummyBallotGateway = new BallotGatewayDummy();
    ProfileGateway noProfileProfileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();

    AddToBallotInteractor interactor = new AddToBallotInteractor(dummyBallotGateway, noProfileProfileGateway);
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse()
  {
    BallotGateway ballotGateway = new GetEmptyBallotBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub(
          new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void gatewayGetsBallotWithChanges()
  {
    GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy ballotGateway = new GetEmptyBallotAndRecordSavedBallotBallotGatewaySpy();
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(
          new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertEquals(profileGateway.getAProfile(), ballotGateway.SavedBallot.get(0));
  }
}
