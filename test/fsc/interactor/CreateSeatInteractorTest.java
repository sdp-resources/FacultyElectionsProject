package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.request.CreateSeatRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateSeatInteractorTest {
  public static final String COMMITTEE_NAME = "a committee";
  public static final String SEAT_NAME = "a seat";
  private static final String QUERY_STRING = "status equals active";
  private CreateSeatRequest request;
  private Seat expectedSeat;
  private CommitteeInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();
  private Committee committee;
  private Query query;

  @Before
  public void setup() throws QueryStringParser.QueryParseException {
    request = new CreateSeatRequest(COMMITTEE_NAME, SEAT_NAME, QUERY_STRING);
    query = new QueryStringConverter().fromString(QUERY_STRING);
    committee = entityFactory.createCommittee(COMMITTEE_NAME, "");
  }

  @Test
  public void WhenCommitteeNameDoesNotExist_thenReturnError() {
    RejectingCommitteeGatewaySpy gateway = new RejectingCommitteeGatewaySpy();
    interactor = new CommitteeInteractor(gateway, null, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ResponseFactory.unknownCommitteeName(), response);
  }

  @Test
  public void WhenSeatNameDoesExist_thenReturnError() {
    expectedSeat = entityFactory.createSeat(SEAT_NAME, query, committee);
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ResponseFactory.resourceExists(), response);
  }

  @Test
  public void WhenSeatNameDoesNotExist_addSeatAndSave() {
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertTrue(committee.hasSeat(SEAT_NAME));
    assertTrue(gateway.saveWasCalled);
    assertEquals(ResponseFactory.success(), response);
  }
}
