package fsc.interactor;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewayStub;
import fsc.mock.gateway.committee.RejectingCommitteeGatewayStub;
import fsc.request.EditCommitteeRequest;
import fsc.response.SuccessResponse;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class EditCommitteeInteractorTest {

  @Test
  public void changeOnNonexistantCommitteeGivesErrorResponse(){
    CommitteeGateway gateway = new RejectingCommitteeGatewayStub();
    EditCommitteeInteractor interactor = new EditCommitteeInteractor(gateway);
    String name = "Steering";
    Map<String, Object> changes = new HashMap();
    changes.put("name", "steering wheel");
    EditCommitteeRequest request = new EditCommitteeRequest(name, changes);

    ErrorResponse response = (ErrorResponse) interactor.execute(request);

    assertEquals("No committee with that name", response.response);
  }

  @Test
  public void changeOnProfileMakesChangeToGateway()
  {
    String originalName = "steering";
    String newName = "steering wheel";
    String description = "Turns the car";

    Map<String, Object> changes = new HashMap();
    changes.put("name", newName);
    EditCommitteeRequest request = new EditCommitteeRequest(originalName, changes);

    Committee initialCommittee = new Committee(originalName, description);
    CommitteeGateway gateway = new AcceptingCommitteeGatewayStub(initialCommittee);
    EditCommitteeInteractor interactor = new EditCommitteeInteractor(gateway);

    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessResponse);
    assertEquals(newName, initialCommittee.getName());
  }

  @Test
  public void canMakeMultipleChanges(){
    String originalName = "steering";
    String newName = "steering wheel";
    String description = "Turns the car";
    String newDescription = "This turns the car real well.";

    Map<String, Object> changes = new HashMap();
    changes.put("name", newName);
    changes.put("description", newDescription);
    EditCommitteeRequest request = new EditCommitteeRequest(originalName, changes);

    Committee initialCommittee = new Committee(originalName, description);
    CommitteeGateway gateway = new AcceptingCommitteeGatewayStub(initialCommittee);
    EditCommitteeInteractor interactor = new EditCommitteeInteractor(gateway);

    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessResponse);
    assertEquals(newName, initialCommittee.getName());
    assertEquals(newDescription, initialCommittee.getDescription());
  }
}

