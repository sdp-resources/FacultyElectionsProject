package fsc.interactor;

import fsc.mock.gateway.division.ExistingDivisionGatewaySpy;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ViewDivisionsTest {

  ViewDivisionRequest request;
  ViewDivisionInteractor interactor;
  Response response;
  private ExistingDivisionGatewaySpy divisionGateway;

  @Before
  public void setup() {
    request = new ViewDivisionRequest();
    divisionGateway = new ExistingDivisionGatewaySpy("SCI", "ART");
  }

  @Test
  public void gatewayDivisionListEqualsOurDivisionList() {
    interactor = new ViewDivisionInteractor(divisionGateway);
    response = interactor.execute(request);
    assertEquals(ViewResponse.ofStrings(divisionGateway.divisions), response);
  }

}
