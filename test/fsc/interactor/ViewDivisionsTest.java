package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.gateway.division.ExistingDivisionGatewaySpy;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewDivisionsTest {

  ViewDivisionRequest request;
  DivisionInteractor interactor;
  Response response;
  private ExistingDivisionGatewaySpy divisionGateway;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    request = new ViewDivisionRequest();
    divisionGateway = new ExistingDivisionGatewaySpy("SCI", "ART");
  }

  @Test
  public void gatewayDivisionListEqualsOurDivisionList() {
    interactor = new DivisionInteractor(divisionGateway, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.ofDivisions(divisionGateway.divisions), response);
  }

}
