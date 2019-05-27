package fsc.interactor;

import fsc.entity.Candidate;
import fsc.mock.ViewDTSGatewayDummy;
import fsc.request.ViewDTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewDTSInteractorTest {

  @Ignore
  @Test
  public void canGetDTSFormForUser() throws ErrorResponse {
    String username = "skiadas21";
    String electionID = "1";
    ViewDTSRequest request = new ViewDTSRequest(username, electionID);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    ViewDTSInteractor interactor = new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(gateway.profile.username, username);
    assertEquals(gateway.candidate.getStatus(), Candidate.Status.NoAnswer);
    assertNotNull(((ViewResponse) response).values);
  }

  @Test
  public void cannotGetDTSFormForUser() throws ErrorResponse {
    String username = "wilson";
    String electionID = "1";
    ViewDTSRequest request = new ViewDTSRequest(username, electionID);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    ViewDTSInteractor interactor = new ViewDTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof ErrorResponse);
  }

}
