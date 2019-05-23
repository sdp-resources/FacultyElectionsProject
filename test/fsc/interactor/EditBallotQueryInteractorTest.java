package fsc.interactor;

import fsc.gateway.ElectionGateway;
import fsc.mock.AlwaysTrueQueryStub;
import fsc.mock.ElectionGatewaySpy;
import fsc.request.EditBallotQueryRequest;
import fsc.response.Response;
import fsc.response.SuccessfullyEditedResponse;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class EditBallotQueryInteractorTest {

  private EditBallotQueryRequest request;

  @Test
  public void isElectionID() throws Exception{
  request = new EditBallotQueryRequest("556", new AlwaysTrueQueryStub());
  ElectionGateway fakeGateway = new ElectionGatewaySpy();
  EditBallotQueryInteractor interactor = new EditBallotQueryInteractor(fakeGateway);
  Response response = (Response) interactor.execute(request);
  assertFalse(response instanceof SuccessfullyEditedResponse);
  }
}

