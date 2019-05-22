package fsc.interactor;

import fsc.mock.viewDTSGateWayDummy;
import fsc.request.viewDTSRequest;
import fsc.response.*;
import org.junit.Test;

public class viewDTSInteractorTest {

  @Test
  public void canGetDTSFormforUser() throws ErrorResponse {
    String username = "a";
    Object electionID = 1;
    viewDTSRequest request = new viewDTSRequest(username, electionID);
    viewDTSGateWayDummy gateway = new viewDTSGateWayDummy();
    viewDTSInteractor interactor =  new viewDTSInteractor(gateway);
    Response response = interactor.execute(request);
  }

}
