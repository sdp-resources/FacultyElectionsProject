package fsc.interactor;

import fsc.mock.ViewDivisionStub;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewDivisionResponse;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class ViewDivisionsTest {

  ViewDivisionRequest request;
  ViewDivisionInteractor interactor;
  Response response;
  ArrayList<String> testList = new ArrayList<String>();

  @Before
  public void setup(){
    testList.add("Banana");
    testList.add("Tree");

  }

  @Test
  public void sendRequestToGetValidResponseBack(){
    request = new ViewDivisionRequest();
    ViewDivisionStub gateway = new ViewDivisionStub();
    interactor = new ViewDivisionInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(response instanceof ViewDivisionResponse);

  }

  @Test
  public void gatewayDivisionListEqualsOurDivisionList(){
    request = new ViewDivisionRequest();
    ViewDivisionStub gateway = new ViewDivisionStub();
    interactor = new ViewDivisionInteractor(gateway);
    response = interactor.execute(request);
    gateway.addDivision("Banana");
    gateway.addDivision("Tree");
    assertEquals(testList, gateway.getAvailableDivisions());
  }

}
