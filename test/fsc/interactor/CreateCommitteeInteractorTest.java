package fsc.interactor;

import fsc.mock.CommitteeAlreadyExistsCommitteeGatewaySpy;
import fsc.mock.CommitteeGatewaySpy;
import fsc.request.CreateCommitteeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCommitteeInteractorTest {
  public static final String COMMITTEE_NAME = "cccc";
  private CreateCommitteeRequest request;

  @Before
  public void setup() {
    request = new CreateCommitteeRequest(COMMITTEE_NAME, "xxxx");
  }

  @Test
  public void ExecuteCreatesCorrectResponseType() {
    CommitteeGatewaySpy gateway = new CommitteeGatewaySpy();
    CreateCommitteeInteractor interactor = new CreateCommitteeInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void ExecuteCreatesAFailedResponse() {
    CommitteeAlreadyExistsCommitteeGatewaySpy gateway = new CommitteeAlreadyExistsCommitteeGatewaySpy();
    CreateCommitteeInteractor interactor = new CreateCommitteeInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ErrorResponse.resourceExists(), response);
  }
}
