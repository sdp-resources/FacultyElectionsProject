package fsc.interactor;

import fsc.entity.Candidate;
import fsc.mock.ViewDTSGatewayDummy;
import fsc.request.ViewDTSRequest;
import fsc.response.*;
import fsc.viewable.ViewableProfile;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewDTSInteractorTest {

  @Test
  public void canGetDTSFormForUser() throws ErrorResponse {
    String username = "skiadas21";
    String electionID = "1";
    ViewDTSRequest request = new ViewDTSRequest(username, electionID);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    ViewDTSInteractor interactor =  new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(gateway.profile.username, username);
    assertEquals(gateway.candidate.getStatus(), Candidate.Status.NoAnswer);
    assertNotNull(((ViewDTSResponse) response).profile);
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
