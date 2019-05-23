package fsc.interactor;

import fsc.entity.Candidate;
import fsc.mock.ViewDTSGatewayDummy;
import fsc.request.ViewDTSRequest;
import fsc.response.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ViewDTSInteractorTest {

  @Test
  public void canGetDTSFormForUser() throws ErrorResponse {
    String username = "skiadas21";
    String electionID = "1";
    ViewDTSRequest request = new ViewDTSRequest(username, electionID);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    ViewDTSInteractor interactor =  new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(gateway.profile.username == username);
    assertTrue(gateway.candidate.getStatus() == Candidate.Status.NoAnswer);
    assertTrue(response instanceof ViewDTSResponse);
  }

  @Test
  public void cannotGetDTSFormForUser() throws ErrorResponse {
    String username = "wilson";
    String electionID = "1";
    ViewDTSRequest request = new ViewDTSRequest(username, electionID);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    ViewDTSInteractor interactor =  new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof ErrorResponse);
  }

}
