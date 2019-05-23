package fsc.interactor;

import fsc.entity.Candidate;
import fsc.mock.DTSGateWayDummy;
import fsc.mock.ViewDTSGatewayDummy;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DTSInteractorTest {

  private String electionID = "12345";
  private String profileUserName = "skiadas21";
  private Candidate.Status status = Candidate.Status.Declined;

  @Test
  public void correctExecuteTest() throws ErrorResponse {
    DTSRequest request = new DTSRequest(electionID, profileUserName, status);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    DTSInteractor interactor = new DTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void badExecuteTest() throws ErrorResponse {
    DTSRequest request = new DTSRequest(electionID, "wilson", status);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    DTSInteractor interactor = new DTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof ErrorResponse);
  }
}

