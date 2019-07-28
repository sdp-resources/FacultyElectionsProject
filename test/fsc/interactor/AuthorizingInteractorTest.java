package fsc.interactor;

import fsc.entity.session.UnauthenticatedSession;
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
                                                                gateway.getEntityFactory(),
                                                                null));
    Request request = new ViewProfilesListRequest();
    request.setSession(new UnauthenticatedSession());
    interactor.handle(request);
  }
}