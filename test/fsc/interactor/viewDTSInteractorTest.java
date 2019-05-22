package fsc.interactor;

import fsc.mock.viewDTSGateWayDummy;
import fsc.request.viewDTSRequest;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import fsc.response.viewDTSResponse;
import org.junit.Test;

public class viewDTSInteractorTest {

  @Test
  public void canGetDTSFormforUser(){
    String username = "a";
    Integer electionID = 1;
    viewDTSRequest request = new viewDTSRequest(username, electionID);
    viewDTSGateWayDummy gateway = new viewDTSGateWayDummy();
    viewDTSInteractor interactor =  new viewDTSInteractor(gateway);
    Response response = interactor.execute(request);

    assert(response instanceof SuccessResponse);
  }

}
