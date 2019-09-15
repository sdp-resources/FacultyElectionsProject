package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.mock.EntityStub;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
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
  private static final long SEAT_ID = 4;
  private EditSeatRequest request;
  private CommitteeInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();
  private Committee committee;
  private Query query;
  private Map<String, String> changes;
  private Seat seat;

  @Before
  public void setup() throws QueryStringParser.QueryParseException {
    changes = new HashMap<>();
    request = new EditSeatRequest(SEAT_ID, changes);
    query = new QueryStringConverter().fromString(QUERY_STRING);
    committee = entityFactory.createCommittee(COMMITTEE_NAME, "", null);
    seat = entityFactory.createSeat(SEAT_NAME, query, committee);
    seat.setId(SEAT_ID);
  }

  @Test
  public void WhenSeatDoesNotExist_returnError() {
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee);
    interactor = new CommitteeInteractor(gateway, null, entityFactory, null);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownSeatName(), response);
    assertFalse(gateway.saveWasCalled);
  }

  @Test
  public void WhenSeatDoesExist_makeRequestedChanges() {
    String newName = "new name";
    Query query = Query.never();
    changes.put(EditSeatRequest.EDIT_SEAT_NAME, newName);
    changes.put(EditSeatRequest.EDIT_SEAT_QUERY, new QueryStringConverter().toString(query));
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee, seat);
    interactor = new CommitteeInteractor(gateway, null, entityFactory, null);
    Response response = interactor.handle(request);

    assertEquals(SEAT_ID, (long) gateway.submittedSeatId);
    assertEquals(ResponseFactory.success(), response);
    assertTrue(gateway.saveWasCalled);
    assertEquals(newName, seat.getName());
    assertEquals(query, seat.getCandidateQuery());
  }

  @Test
  public void whenProfileIsMissing_returnAppropriateError() {
    String profileName = "a profile name";
    changes.put(EditSeatRequest.EDIT_SEAT_PROFILE, profileName);
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee, seat);
    InvalidProfileGatewaySpy profileGateway = new InvalidProfileGatewaySpy();
    interactor = new CommitteeInteractor(gateway, profileGateway, entityFactory, null);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownProfileName(), response);
    assertEquals(profileName, profileGateway.submittedUsername);
    assertFalse(gateway.saveWasCalled);
    assertNull(seat.getProfile());
  }

  @Test
  public void whenChangeOfProfileRequestedAndProfileExists_makeTheChange() {
    Profile profile = EntityStub.getProfile(0);
    changes.put(EditSeatRequest.EDIT_SEAT_PROFILE, profile.getUsername());
    ProvidedCommitteeGatewaySpy gateway = new ProvidedCommitteeGatewaySpy(committee, seat);
    ProfileGatewayStub profileGateway = new ProfileGatewayStub(profile);
    interactor = new CommitteeInteractor(gateway, profileGateway, entityFactory, null);
    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.success(), response);
    assertTrue(gateway.saveWasCalled);
    assertEquals(profile, seat.getProfile());
  }
}
