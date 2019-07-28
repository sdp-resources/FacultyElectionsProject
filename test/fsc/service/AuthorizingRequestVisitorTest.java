package fsc.service;

import fsc.entity.session.Session;
import fsc.gateway.Gateway;
import fsc.mock.EntityStub;
import fsc.request.*;
import gateway.InMemoryGateway;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorizingRequestVisitorTest {

  private Gateway gateway = new InMemoryGateway();
  private AuthorizingRequestVisitor authorizer = new AuthorizingRequestVisitor(gateway);

  @Test
  public void viewProfilesListOnlyAuthorizedForAdmins() {
    assertIsAuthorized(new ViewProfilesListRequest(), admin());
    assertIsNotAuthorized(new ViewProfilesListRequest(), user("skiadas"));
    assertIsNotAuthorized(new ViewProfilesListRequest(), unauthenticated());
  }

  @Test
  public void viewProfileOnlyAuthorizedForAdminsOrTheUser() {
    assertIsAuthorized(new ViewProfileRequest("skiadas"), admin());
    assertIsAuthorized(new ViewProfileRequest("skiadas"), user("skiadas"));
    assertIsNotAuthorized(new ViewProfileRequest("skiadas"), user("other"));
    assertIsNotAuthorized(new ViewProfileRequest("skiadas"), unauthenticated());
  }

  @Test
  public void createProfileOnlyAuthorizedForAdmins() {
    assertIsAuthorized(createProfile(), admin());
    assertIsNotAuthorized(createProfile(), user("skiadas"));
    assertIsNotAuthorized(createProfile(), user("other"));
    assertIsNotAuthorized(createProfile(), unauthenticated());
  }

  private void assertIsNotAuthorized(Request request, Session session) {
    request.setSession(session);
    assertEquals(false, authorizer.isAuthorized(request));
  }

  private void assertIsAuthorized(Request request, Session session) {
    request.setSession(session);
    assertEquals(true, authorizer.isAuthorized(request));
  }

  private Session user(String username) {
    return EntityStub.userSession(username);
  }

  private Session admin() {
    return EntityStub.adminSession();
  }

  private Session unauthenticated() {
    return EntityStub.unauthenticatedSession();
  }

  public CreateProfileRequest createProfile() {
    return new CreateProfileRequest("name", "skiadas", null, null);
  }

}