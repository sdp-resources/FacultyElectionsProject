package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.gateway.division.ExistingDivisionGatewaySpy;
import fsc.request.ViewDivisionListRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewDivisionsTest {

  ViewDivisionListRequest request;
  DivisionInteractor interactor;
  Response response;
  private ExistingDivisionGatewaySpy divisionGateway;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    request = new ViewDivisionListRequest();
    divisionGateway = new ExistingDivisionGatewaySpy("SCI", "ART");
  }

  @Test
  public void gatewayDivisionListEqualsOurDivisionList() {
    interactor = new DivisionInteractor(divisionGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.ofDivisions(divisionGateway.divisions), response);
  }

}
