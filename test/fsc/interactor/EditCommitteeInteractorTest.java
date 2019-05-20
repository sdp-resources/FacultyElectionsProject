package fsc.interactor;

import fsc.gateway.CommitteeGateway;
import fsc.request.EditCommitteeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EditCommitteeInteractorTest {

  @Test
  public void changeOnNonexistantCommitteeGivesErrorResponse(){
    EditCommitteeInteractor interactor = new EditCommitteeInteractor();
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

  }
}

