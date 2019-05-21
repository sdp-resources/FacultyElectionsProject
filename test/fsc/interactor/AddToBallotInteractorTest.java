package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.*;
import fsc.request.AddToBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyAddedProfileToBallotResponse;
import org.junit.Before;
import org.junit.Test;

import static fsc.response.ErrorResponse.NO_BALLOT_WITH_THAT_ID;
import static fsc.response.ErrorResponse.NO_PROFILE_WITH_THAT_USERNAME;
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

    assertEquals( NO_BALLOT_WITH_THAT_ID, ((ErrorResponse) response).response);
  }

  @Test
  public void addingNotRealProfile()
  {
    BallotGateway dummyBallotGateway = new BallotGatewayDummy();
    ProfileGateway noProfileProfileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();

    AddToBallotInteractor interactor = new AddToBallotInteractor(dummyBallotGateway, noProfileProfileGateway);
    Response response = interactor.execute(request);

    assertEquals(NO_PROFILE_WITH_THAT_USERNAME, ((ErrorResponse) response).response);
  }

  @Test
  public void addRealProfileToRealBallotGivesSuccessfullyAddedToBallotResponse()
  {
    BallotGateway ballotGateway = new GetEmptyBallotBallotGatewayStub();
    ProfileGateway profileGateway = new ProfileGatewayStub(
          new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));

    AddToBallotInteractor interactor = new AddToBallotInteractor(ballotGateway, profileGateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessfullyAddedProfileToBallotResponse);
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
