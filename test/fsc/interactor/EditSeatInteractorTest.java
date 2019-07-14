package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.mock.EntityStub;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.EditSeatRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class EditSeatInteractorTest {
  public static final String COMMITTEE_NAME = "a committee";
  public static final String SEAT_NAME = "a seat";
  private static final String QUERY_STRING = "status equals active";
  private EditSeatRequest request;
  private Seat expectedSeat;
  private CommitteeInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();
  private Committee committee;
  private Query query;
  private Map<String, String> changes;

  @Before
  public void setup() throws QueryStringParser.QueryParseException {
    changes = new HashMap<>();
    request = new EditSeatRequest(COMMITTEE_NAME, SEAT_NAME, changes);
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
  public void WhenSeatNameDoesNotExist_returnError() {
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownSeatName(), response);
    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertFalse(gateway.saveWasCalled);
  }

  @Test
  public void WhenSeatNameDoesExist_makeRequestedChanges() {
    String newName = "new name";
    Query query = Query.never();
    changes.put(EditSeatRequest.EDIT_SEAT_NAME, newName);
    changes.put(EditSeatRequest.EDIT_SEAT_QUERY, new QueryStringConverter().toString(query));
    expectedSeat = entityFactory.createSeat(SEAT_NAME, query, committee);
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ResponseFactory.success(), response);
    assertTrue(gateway.saveWasCalled);
    assertEquals(newName, expectedSeat.getName());
    assertEquals(query, expectedSeat.getDefaultQuery());
  }

  @Test
  public void whenProfileIsMissing_returnAppropriateError() {
    String profileName = "a profile name";
    changes.put(EditSeatRequest.EDIT_SEAT_PROFILE, profileName);
    expectedSeat = entityFactory.createSeat(SEAT_NAME, query, committee);
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    InvalidProfileGatewaySpy profileGateway = new InvalidProfileGatewaySpy();
    interactor = new CommitteeInteractor(gateway, profileGateway, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.unknownProfileName(), response);
    assertEquals(profileName, profileGateway.submittedUsername);
    assertFalse(gateway.saveWasCalled);
    assertNull(expectedSeat.getProfile());
  }

  @Test
  public void whenChangeOfProfileRequestedAndProfileExists_makeTheChange() {
    Profile profile = EntityStub.getProfile(0);
    changes.put(EditSeatRequest.EDIT_SEAT_PROFILE, profile.getUsername());
    expectedSeat = entityFactory.createSeat(SEAT_NAME, query, committee);
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(profile);
    interactor = new CommitteeInteractor(gateway, profileGateway, entityFactory);
    Response response = interactor.execute(request);

    assertEquals(ResponseFactory.success(), response);
    assertTrue(gateway.saveWasCalled);
    assertEquals(profile, expectedSeat.getProfile());
  }
}
