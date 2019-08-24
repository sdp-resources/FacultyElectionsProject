package webserver;

import fsc.app.AppContext;
import spark.Request;
import spark.Response;

public class LoginRequestHandler extends RequestHandler {

  public LoginRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processGetIndex() {
    loadSession();
    if (session.role.equals("ROLE_ADMIN")) return redirect("/admin");
    if (session.role.equals("ROLE_USER")) return redirect("/user");
    return null;
  }

  String processGetLogin() {
    return serveTemplate("/login.handlebars");
  }

  Object processPostLogin() {
    fsc.response.Response response = appContext.login(getRequestParameter("username"),
                                                      getRequestParameter("password"));
    if (!response.isSuccessful())
      return redirectWithFlash("/login", response);

    storeSession(response.getValues());
    return redirect("/");
  }

  public Object processGetLogout() {
    invalidateCurrentSession();
    return redirect("/login");
  }

}
