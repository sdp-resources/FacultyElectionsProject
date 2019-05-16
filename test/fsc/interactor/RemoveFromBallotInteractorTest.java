package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.mock.NoBallotExistsBallotGatewayStub;
import fsc.mock.ProfileGatewayDummy;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RemoveFromBallotInteractorTest {

  private RemoveFromBallotRequest request;

  @Test
  public void ballotDoesNotExist(){

    NoBallotExistsBallotGatewayStub noBallotBallotGateway = new NoBallotExistsBallotGatewayStub();
    ProfileGateway dummyProfileGateway = new ProfileGatewayDummy();

    RemoveFromBallotInteractor inter = new RemoveFromBallotInteractor(noBallotBallotGateway,
                                                            dummyProfileGateway);
    Response response = inter.execute(request);

    assertEquals("No ballot with that ID", ((ErrorResponse)response).response);
  }
}
