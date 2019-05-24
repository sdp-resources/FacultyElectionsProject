package fsc.interactor;

import fsc.entity.Candidate;
import fsc.mock.ViewDTSGatewayDummy;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DTSInteractorTest {

  private final String electionID = "12345";
  private final String profileUserName = "skiadas21";
  private final Candidate.Status status = Candidate.Status.Declined;

  @Test
  public void correctExecuteTest() {
    DTSRequest request = new DTSRequest(electionID, profileUserName, status);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    DTSInteractor interactor = new DTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void badExecuteTest() {
    DTSRequest request = new DTSRequest(electionID, "wilson", status);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    DTSInteractor interactor = new DTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof ErrorResponse);
  }
}

