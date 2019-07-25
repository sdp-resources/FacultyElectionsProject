package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.gateway.division.ExistingDivisionGatewaySpy;
import fsc.mock.gateway.division.MissingDivisionGatewaySpy;
import fsc.request.AddDivisionRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DivisionInteractorTest {
  private static final String A_DIVISION = "ENG";
  private AddDivisionRequest request;
  private DivisionInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    request = new AddDivisionRequest(A_DIVISION);
  }

  @Test
  public void testCorrectExecute() {
    MissingDivisionGatewaySpy testGateway = new MissingDivisionGatewaySpy();
    interactor = new DivisionInteractor(testGateway, entityFactory);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.success(), response);
    assertEquals(List.of("has division: " + A_DIVISION, "add division: " + A_DIVISION, "save"),
                 testGateway.events);
  }

  @Test
  public void testAlreadyExistsExecute() {
    ExistingDivisionGatewaySpy testGateway = new ExistingDivisionGatewaySpy();
    interactor = new DivisionInteractor(testGateway, entityFactory);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.resourceExists(), response);
    assertEquals(List.of("has division: " + A_DIVISION), testGateway.events);
  }
}
