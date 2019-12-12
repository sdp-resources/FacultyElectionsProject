package fsc.interactor;

import fsc.entity.PasswordRecord;
import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.gateway.PasswordGateway;
import fsc.gateway.SessionGateway;
import fsc.request.AddPasswordRecordRequest;
import fsc.request.LoginRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.Authenticator;
import fsc.service.Authorizer;
import fsc.service.Credentials;

public class LoginInteractor extends Interactor {
  private SessionGateway sessionGateway;
  private Authenticator authenticator;
  private PasswordGateway passwordGateway;

  public LoginInteractor(
        SessionGateway sessionGateway, Authenticator authenticator,
        PasswordGateway passwordGateway
  ) {
    this.sessionGateway = sessionGateway;
    this.authenticator = authenticator;
    this.passwordGateway = passwordGateway;
  }

  public Response execute(LoginRequest request) {
    Credentials credentials = new Credentials(request.username, request.password);
    Session session = authenticator.authenticateWithCredentials(credentials);
    if (!session.isAuthenticated()) {
      return ResponseFactory.invalidCredentials();
    }
    AuthenticatedSession authenticatedSession = (AuthenticatedSession) session;
    sessionGateway.addSession(authenticatedSession);
    sessionGateway.save();
    return ResponseFactory.ofAuthenticatedSession(authenticatedSession);
  }

  public Response execute(AddPasswordRecordRequest request) {
    if (passwordGateway.hasPasswordRecordFor(request.username)) {
      return ResponseFactory.resourceExists();
    }
    Authorizer.Role role = Authorizer.Role.valueOf(request.role);
    PasswordRecord record = PasswordRecord.create(request.username, request.password, role);
    passwordGateway.addPasswordRecord(record);
    passwordGateway.save();
    return ResponseFactory.success();
  }
}
