package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.request.CreateSeatRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.query.AcceptingNameValidator;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateSeatInteractorTest {
  public static final String COMMITTEE_NAME = "a committee";
  public static final String SEAT_NAME = "a seat";
  private Long COMMITTEE_ID = Long.valueOf(5);
  private static final String QUERY_STRING = "status equals active";
  private CreateSeatRequest request;
  private Seat expectedSeat;
  private CommitteeInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();
  private Committee committee;
  private Query query;
  private AcceptingNameValidator nameValidator;

  @Before
  public void setup() throws QueryStringParser.QueryParseException {
    request = new CreateSeatRequest(COMMITTEE_ID, SEAT_NAME, QUERY_STRING);
    query = new QueryStringConverter().fromString(QUERY_STRING);
    committee = entityFactory.createCommittee(COMMITTEE_NAME, "", null);
    nameValidator = new AcceptingNameValidator();
  }

  @Test
  public void WhenCommitteeNameDoesNotExist_thenReturnError() {
    RejectingCommitteeGatewaySpy gateway = new RejectingCommitteeGatewaySpy();
    interactor = new CommitteeInteractor(gateway, null, entityFactory, nameValidator);
    Response response = interactor.handle(request);

    assertEquals(COMMITTEE_ID, gateway.submittedCommitteeId);
    assertEquals(ResponseFactory.unknownCommitteeName(), response);
  }

  @Test
  public void WhenSeatNameDoesExist_thenReturnError() {
    expectedSeat = entityFactory.createSeat(SEAT_NAME, query, committee);
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory, nameValidator);
    Response response = interactor.handle(request);

    assertEquals(COMMITTEE_ID, gateway.submittedCommitteeId);
    assertEquals(ResponseFactory.resourceExists(), response);
  }

  @Test
  public void WhenQueryStringIsInvalid_thenReturnError() {
    request = new CreateSeatRequest(COMMITTEE_ID, SEAT_NAME, "anInvalid query string");
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory, nameValidator);
    Response response = interactor.handle(request);
    assertFalse(response.isSuccessful());
    assertFalse(gateway.saveWasCalled);
  }

  @Test
  public void WhenSeatNameDoesNotExist_addSeatAndSave() {
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory, nameValidator);
    Response response = interactor.handle(request);

    assertEquals(COMMITTEE_ID, gateway.submittedCommitteeId);
    assertTrue(committee.hasSeat(SEAT_NAME));
    assertTrue(gateway.saveWasCalled);
    assertEquals(ResponseFactory.success(), response);
  }
}
