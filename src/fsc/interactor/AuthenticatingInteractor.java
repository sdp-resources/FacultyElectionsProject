package fsc.interactor;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;
import fsc.gateway.SessionGateway;
import fsc.request.Request;
import fsc.response.Response;
import fsc.response.ResponseFactory;

import java.time.LocalDateTime;

public class AuthenticatingInteractor extends Interactor {
  private SessionGateway sessionGateway;

  public AuthenticatingInteractor(SessionGateway sessionGateway) {
    this.sessionGateway = sessionGateway;
  }

  @Override
  public <T extends Request> Response handle(T request) {
    try {
      if (request.getSession() == null) {
        request.setSession(authenticate(request));
      }

      return delegate(request);
    } catch (SessionGateway.InvalidOrExpiredTokenException e) {
      return ResponseFactory.invalidSession();
    }
  }

  // TODO: How to handle timed out requests and the database?
  // Need to periodically clean them?
  private Session authenticate(Request request)
        throws SessionGateway.InvalidOrExpiredTokenException {
    return request.token == null ? new UnauthenticatedSession()
                                 : getActiveSession(request);
  }

  private AuthenticatedSession getActiveSession(Request request)
        throws SessionGateway.InvalidOrExpiredTokenException {
    AuthenticatedSession session = sessionGateway.getActiveSession(request.token);
    session.setExpirationTime(thirtyMinutesFromNow());
    sessionGateway.save();

    return session;
  }

  // TODO: Find a better way to standardize session duration
  private LocalDateTime thirtyMinutesFromNow() {
    return LocalDateTime.now().plusMinutes(30);
  }

}