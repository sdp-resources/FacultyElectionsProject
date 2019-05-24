package webserver;

import fsc.gateway.ContractTypeGateway;
import fsc.gateway.Gateway;
import fsc.interactor.CreateProfileInteractor;
import fsc.interactor.ViewContractsInteractor;
import fsc.request.ContractsViewerRequest;
import fsc.request.CreateProfileRequest;
import fsc.response.Response;
import fsc.response.ViewContractsResponse;
import gateway.InMemoryGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InteractionControllerTest {

  private InMemoryGateway gateway;
  private InteractionController controller;

  @Before
  public void setUp() {
    gateway = new InMemoryGatewaySpy();
    controller = new InteractionController(gateway);
  }

  @Test
  public void createProfileControllerSubmitsCorrectProfile() {
    CreateProfileInteractorSpy interactor = new CreateProfileInteractorSpy(gateway);
    controller.setCreateProfileInteractor(interactor);
    Map<String, String> map = simpleMap("fullName", "Skiadas", "username", "skiadas", "division",
                                        "Natural Sciences", "contractType", "tenured");

    Response response = controller.createProfile(map::get);
    CreateProfileRequest submittedRequest = interactor.submittedRequest;
    assertEquals(submittedRequest.username, map.get("username"));
    assertEquals(submittedRequest.name, map.get("fullName"));
    assertEquals(submittedRequest.division, map.get("division"));
    assertEquals(submittedRequest.contract, map.get("contractType"));
  }

  @Test
  public void canGetContractTypes() {
    ViewContractsInteractor interactor = new ViewContractsInteractorSpy(gateway);
    List<String> contractTypes = controller.getAllContractTypes();
  }

  private Map<String, String> simpleMap(String... strings) {
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < strings.length; i += 2) {
      map.put(strings[i], strings[i + 1]);
    }

    return map;
  }

  private class InMemoryGatewaySpy extends InMemoryGateway {}

  private class CreateProfileInteractorSpy extends CreateProfileInteractor {
    CreateProfileRequest submittedRequest = null;

    CreateProfileInteractorSpy(Gateway gateway) {
      super(gateway);
    }

    public Response execute(CreateProfileRequest request) {
      submittedRequest = request;
      return super.execute(request);
    }
  }

  private class ViewContractsInteractorSpy extends ViewContractsInteractor {
    private ContractsViewerRequest submittedRequest;

    public ViewContractsResponse execute(ContractsViewerRequest request) {
      submittedRequest = request;
      return super.execute(submittedRequest);
    }

    ViewContractsInteractorSpy(ContractTypeGateway gateway) {
      super(gateway);
    }
  }
}