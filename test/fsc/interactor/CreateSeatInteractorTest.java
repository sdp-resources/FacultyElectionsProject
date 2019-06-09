package fsc.interactor;

import fsc.entity.Committee;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.request.CreateSeatRequest;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import fsc.service.query.QueryStringConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateSeatInteractorTest {
  public static final String COMMITTEE_NAME = "a committee";
  public static final String SEAT_NAME = "a seat";
  private static final String QUERY_STRING = "status equals active";
  private CreateSeatRequest request;
  private Seat expectedSeat;
  private CreateSeatInteractor interactor;

  @Before
  public void setup() {
    request = new CreateSeatRequest(COMMITTEE_NAME, SEAT_NAME, QUERY_STRING);
    Query query = new QueryStringConverter().fromString(QUERY_STRING);
    expectedSeat = new Seat(SEAT_NAME, query);
  }

  @Test
  public void WhenCommitteeNameDoesNotExist_thenReturnError() {
    RejectingCommitteeGatewaySpy gateway = new RejectingCommitteeGatewaySpy();
    interactor = new CreateSeatInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ErrorResponse.unknownCommitteeName(), response);
  }

  @Test
  public void WhenSeatNameDoesExist_thenReturnError() {
    Committee committee = new Committee(COMMITTEE_NAME, "");
    committee.addSeat(expectedSeat);
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CreateSeatInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ErrorResponse.resourceExists(), response);
  }

  @Test
  public void WhenSeatNameDoesNotExist_addSeatAndSave() {
    Committee committee = new Committee(COMMITTEE_NAME, "");
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CreateSeatInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertTrue(committee.hasSeat(SEAT_NAME));
    assertTrue(gateway.saveWasCalled);
    assertEquals(new SuccessResponse(), response);
  }
}
