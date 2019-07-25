package fsc.request;

import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;

public abstract class Request {
  public String token;
  private Session session = new UnauthenticatedSession();

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public abstract Object accept(RequestVisitor visitor);
}
