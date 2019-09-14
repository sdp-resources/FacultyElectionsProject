package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.request.EditCommitteeRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EditCommitteeInteractorTest {

  private CommitteeInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();
  private int committeeId = 0;

  @Test
  public void changeOnNonexistantCommitteeGivesErrorResponse() {
    interactor = new CommitteeInteractor(new RejectingCommitteeGatewaySpy(), null, entityFactory, null);
    HashMap<String, Object> changes = new HashMap<>();
    changes.put("name", "steering wheel");
    EditCommitteeRequest request = new EditCommitteeRequest(0, changes);

    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.unknownCommitteeName(), response);
  }

  @Test
  public void changeOnProfileMakesChangeToGateway() {
    String newName = "steering wheel";

    Map<String, Object> changes = new HashMap<>();
    changes.put("name", newName);
    EditCommitteeRequest request = new EditCommitteeRequest(committeeId, changes);

    AcceptingCommitteeGatewaySpy gateway = new AcceptingCommitteeGatewaySpy();
    interactor = new CommitteeInteractor(gateway, null, entityFactory, null);

    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.success(), response);
    assertEquals(committeeId, (long) gateway.submittedCommitteeId);
    assertEquals(newName, gateway.returnedCommittee.getName());
  }

  @Test
  public void canMakeMultipleChanges() {
    String newName = "steering wheel";
    String newDescription = "This turns the car real well.";

    Map<String, Object> changes = new HashMap<>();
    changes.put("name", newName);
    changes.put("description", newDescription);
    EditCommitteeRequest request = new EditCommitteeRequest(committeeId, changes);

    AcceptingCommitteeGatewaySpy gateway = new AcceptingCommitteeGatewaySpy();
    interactor = new CommitteeInteractor(gateway, null, entityFactory, null);

    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.success(), response);
    assertEquals(committeeId, (long) gateway.submittedCommitteeId);
    assertEquals(newName, gateway.returnedCommittee.getName());
    assertEquals(newDescription, gateway.returnedCommittee.getDescription());
  }
}

