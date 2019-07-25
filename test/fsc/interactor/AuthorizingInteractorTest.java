package fsc.interactor;

import fsc.gateway.Gateway;
import fsc.request.Request;
import fsc.request.ViewProfilesListRequest;
import gateway.InMemoryGateway;
import org.junit.Test;

public class AuthorizingInteractorTest {
  @Test
  public void authorizingInteractor_cannotHandleAnyRequests() {
    Gateway gateway = new InMemoryGateway();
    Interactor interactor = new AuthorizingInteractor(gateway)
                                  .append(new ProfileInteractor(gateway,
                                                                gateway.getEntityFactory()));
    Request request = new ViewProfilesListRequest();
    interactor.handle(request);
  }
}