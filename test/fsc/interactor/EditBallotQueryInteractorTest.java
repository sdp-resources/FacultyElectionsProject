package fsc.interactor;

import fsc.mock.AlwaysTrueQueryStub;
import fsc.mock.ElectingWithEverythingCorrectGatewaySpy;
import fsc.request.EditBallotQueryRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyEditedResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditBallotQueryInteractorTest {

  private EditBallotQueryRequest request;

  @Test
  public void isElectionID() throws Exception{
  request = new EditBallotQueryRequest("556", new AlwaysTrueQueryStub());
  ElectingWithEverythingCorrectGatewaySpy fakeGateway = new ElectingWithEverythingCorrectGatewaySpy();
  EditBallotQueryInteractor interactor = new EditBallotQueryInteractor(fakeGateway);
  Response response = (Response) interactor.execute(request);
  assertFalse(response instanceof SuccessfullyEditedResponse);
  }
}

