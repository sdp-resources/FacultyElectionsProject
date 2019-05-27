package fsc.interactor;

import fsc.entity.Committee;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.request.CreateCommitteeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCommitteeInteractorTest {
  public static final String COMMITTEE_NAME = "cccc";
  public static final String COMMITTEE_DESCRIPTION = "xxxx";
  private CreateCommitteeRequest request;
  private Committee expectedCommittee;

  @Before
  public void setup() {
    request = new CreateCommitteeRequest(COMMITTEE_NAME, COMMITTEE_DESCRIPTION);
    expectedCommittee = new Committee(COMMITTEE_NAME, COMMITTEE_DESCRIPTION);
  }

  @Test
  public void ExecuteCreatesCorrectResponseType() {
    RejectingCommitteeGatewaySpy gateway = new RejectingCommitteeGatewaySpy();
    CreateCommitteeInteractor interactor = new CreateCommitteeInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertNotNull(gateway.committeeAdded);
    assertEquals(expectedCommittee, gateway.committeeAdded);
    assertTrue(gateway.hasSaved);
    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void ExecuteCreatesAFailedResponse() {
    AcceptingCommitteeGatewaySpy gateway = new AcceptingCommitteeGatewaySpy();
    CreateCommitteeInteractor interactor = new CreateCommitteeInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ErrorResponse.resourceExists(), response);
  }
}
