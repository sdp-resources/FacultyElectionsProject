package fsc.interactor;

import fsc.mock.CommitteeAlreadyExistsCommitteeGatewaySpy;
import fsc.mock.CommitteeGatewaySpy;
import fsc.request.CreateCommitteeRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCommitteeInteractorTest {
  CreateCommitteeRequest request;

  @Before
  public void setup() {
    request = new CreateCommitteeRequest("cccc", "xxxx");
  }

  @Test
  public void ExecuteCreatesCorrectResponseType(){
    CommitteeGatewaySpy gateway = new CommitteeGatewaySpy();
    CreateCommitteeInteractor interactor = new CreateCommitteeInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals("cccc", gateway.submittedCommitteeName);
    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void ExecuteCreatesAFailedResponse() {
    CommitteeAlreadyExistsCommitteeGatewaySpy gateway = new CommitteeAlreadyExistsCommitteeGatewaySpy();
    CreateCommitteeInteractor interactor = new CreateCommitteeInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals("cccc", gateway.submittedCommitteeName);
    assertEquals("Failed to create committee", ((ErrorResponse)response).message);
  }
}
