package fsc.interactor;

import fsc.mock.viewDTSGateWayDummy;
import fsc.request.ViewDTSRequest;
import fsc.response.*;
import org.junit.Test;

public class viewDTSInteractorTest {

  @Test
  public void canGetDTSFormforUser() throws ErrorResponse {
    String username = "a";
    String electionID = "1";
    ViewDTSRequest request = new ViewDTSRequest(username, electionID);
    viewDTSGateWayDummy gateway = new viewDTSGateWayDummy();
    viewDTSInteractor interactor =  new viewDTSInteractor(gateway);
    Response response = interactor.execute(request);
  }

}
