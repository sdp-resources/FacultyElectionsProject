package fsc.interactor;

import fsc.mock.viewDTSGatewayDummy;
import fsc.request.ViewDTSRequest;
import fsc.response.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class viewDTSInteractorTest {

  @Test
  public void canGetDTSFormForUser() throws ErrorResponse {
    String username = "skiadas21";
    String electionID = "1";
    ViewDTSRequest request = new ViewDTSRequest(username, electionID);
    viewDTSGatewayDummy gateway = new viewDTSGatewayDummy();
    viewDTSInteractor interactor =  new viewDTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof viewDTSResponse);
  }

}
