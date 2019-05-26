package fsc.interactor;

import fsc.mock.ViewDivisionStub;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ViewDivisionsTest {

  ViewDivisionRequest request;
  ViewDivisionInteractor interactor;
  ViewResponse<List<String>> response;
  private ViewDivisionStub divisionGateway;

  @Before
  public void setup() {
    request = new ViewDivisionRequest();
    divisionGateway = new ViewDivisionStub("SCI", "ART");
  }

  @Test
  public void gatewayDivisionListEqualsOurDivisionList() {
    interactor = new ViewDivisionInteractor(divisionGateway);
    response = interactor.execute(request);
    assertEquals(divisionGateway.divisions, response.values);
  }

}
