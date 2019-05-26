package fsc.interactor;

import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.AlwaysTrueQueryStub;
import fsc.mock.ElectionGatewaySpy;
import fsc.request.EditBallotQueryRequest;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditBallotQueryInteractorTest {

  private EditBallotQueryRequest request;

  @Ignore
  @Test
  public void isElectionID() {
    request = new EditBallotQueryRequest("556", new AlwaysTrueQueryStub());
    ElectionGateway fakeGateway = new ElectionGatewaySpy();
    ProfileGateway profileGateway = null;
    EditBallotQueryInteractor interactor = new EditBallotQueryInteractor(fakeGateway, null);
    Response response = interactor.execute(request);
    assertEquals(new SuccessResponse(), response);
  }
}

