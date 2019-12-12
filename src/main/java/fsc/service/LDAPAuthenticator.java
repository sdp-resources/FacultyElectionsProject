package fsc.service;

import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LDAPAuthenticator implements Authenticator {

  private Hashtable<String, String> env;
  private SessionCreator sessionCreator;

  public LDAPAuthenticator(SessionCreator sessionCreator) {
    initializeContext();
    this.sessionCreator = sessionCreator;
  }

  public Session authenticateWithCredentials(Credentials credentials) {
    boolean authenticated = authenticate(credentials.getUsername(), credentials.getPassword());
    if (authenticated) {
      return sessionCreator.createSession(Authorizer.Role.ROLE_USER,
                                          credentials.getUsername());
    } else {
      return new UnauthenticatedSession();
    }
  }

  boolean authenticate(String username, String password) {
    try {
      InitialDirContext ctx = getContext(username, password);
      ctx.close();
      return true;
    } catch (NamingException e) {
      return false;
    }
  }

  private InitialDirContext getContext(String username, String password) throws NamingException {
    env.put(Context.SECURITY_PRINCIPAL, "CAMPUS\\" + username);
    env.put(Context.SECURITY_CREDENTIALS, password);
    return new InitialDirContext(env);
  }

  private void initializeContext() {
    env = new Hashtable<>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldaps://delta.campus.hanover.edu:636");
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
  }

}