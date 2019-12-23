package webserver;

import fsc.app.AppContext;
import fsc.viewable.ViewableSession;
import spark.Request;
import spark.Response;

public class LoginRequestHandler extends RequestHandler {

  public LoginRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processGetIndex() {
    loadSession();
    if (session.role.equals("ROLE_ADMIN")) return redirect(Path.admin());
    if (session.role.equals("ROLE_USER")) return redirect(Path.user());
    return null;
  }

  String processGetLogin() {
    return serveTemplate("/login.handlebars");
  }

  Object processPostLogin() {
    fsc.response.Response<ViewableSession> response = appContext
                                                            .login(getRequestParameter("username"),
                                                                   getRequestParameter("password"));
    if (!response.isSuccessful()) { return redirectWithFlash(Path.login(), response); }

    ViewableSession session = response.getValues();
    storeSession(session);
    return redirect(Path.root());
  }

  public Object processGetLogout() {
    invalidateCurrentSession();
    return redirect(Path.login());
  }

}
