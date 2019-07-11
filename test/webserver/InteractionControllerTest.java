package webserver;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.gateway.Gateway;
import fsc.interactor.ProfileInteractor;
import fsc.request.CreateProfileRequest;
import fsc.response.Response;
import gateway.InMemoryGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InteractionControllerTest {

  private Gateway gateway;
  private InteractionController controller;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    gateway = new InMemoryGatewaySpy();
    controller = new InteractionController(gateway);
  }

  @Test
  public void createProfileControllerSubmitsCorrectProfile() {
    ProfileInteractorSpy interactor = new ProfileInteractorSpy(gateway, entityFactory);
    controller.setProfileInteractor(interactor);
    Map<String, String> map = simpleMap("fullName", "Skiadas", "username", "skiadas", "division",
                                        "Natural Sciences", "contractType", "tenured");

    Response response = controller.createProfile(map::get);
    CreateProfileRequest submittedRequest = interactor.submittedRequest;
    assertEquals(submittedRequest.username, map.get("username"));
    assertEquals(submittedRequest.name, map.get("fullName"));
    assertEquals(submittedRequest.division, map.get("division"));
    assertEquals(submittedRequest.contract, map.get("contractType"));
  }

  private Map<String, String> simpleMap(String... strings) {
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < strings.length; i += 2) {
      map.put(strings[i], strings[i + 1]);
    }

    return map;
  }

  private class InMemoryGatewaySpy extends InMemoryGateway {}

  private class ProfileInteractorSpy extends ProfileInteractor {
    CreateProfileRequest submittedRequest = null;

    ProfileInteractorSpy(Gateway gateway, EntityFactory entityFactory) {
      super(gateway, entityFactory);
    }

    public Response execute(CreateProfileRequest request) {
      submittedRequest = request;
      return super.execute(request);
    }
  }

}