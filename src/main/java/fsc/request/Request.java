package fsc.request;

import fsc.entity.session.Session;

public abstract class Request {
  public String token;
  private Session session = null;

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public abstract Object accept(RequestVisitor visitor);
}
