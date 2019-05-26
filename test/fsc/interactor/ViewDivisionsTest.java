package fsc.interactor;

import fsc.mock.ViewDivisionStub;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewDivisionsTest {

  ViewDivisionRequest request;
  ViewDivisionInteractor interactor;
  Response response;
  ArrayList<String> testList = new ArrayList<>();

  @Before
  public void setup() {
    testList.add("SCI");
    testList.add("ART");

  }

  @Test
  public void sendRequestToGetValidResponseBack() {
    request = new ViewDivisionRequest();
    ViewDivisionStub gateway = new ViewDivisionStub();
    interactor = new ViewDivisionInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(response instanceof ViewResponse);

  }

  @Test
  public void gatewayDivisionListEqualsOurDivisionList() {
    request = new ViewDivisionRequest();
    ViewDivisionStub gateway = new ViewDivisionStub();
    interactor = new ViewDivisionInteractor(gateway);
    response = interactor.execute(request);
    gateway.addDivision("SCI");
    gateway.addDivision("ART");
    assertEquals(testList, gateway.getAvailableDivisions());
    assertEquals(testList, ((ViewResponse<List<String>>) response).values);
  }

}
