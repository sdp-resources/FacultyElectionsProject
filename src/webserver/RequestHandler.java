package webserver;

import fsc.app.AppContext;
import fsc.response.ErrorResponse;
import fsc.viewable.ViewableProfile;
import fsc.viewable.ViewableSession;
import spark.*;

import java.util.HashMap;

public class RequestHandler {
  private static final MyHandlebarsTemplateEngine templateEngine = new MyHandlebarsTemplateEngine();
  private final Request req;
  private final Response res;
  protected AppContext appContext;
  protected HashMap<Object, Object> model = new HashMap<>();
  protected ViewableSession session;

  {
    templateEngine.registerHelper("path", HandlebarsHelpers.path());
  }

  public RequestHandler(
        Request req, Response res, AppContext appContext
  ) {
    this.req = req;
    this.res = res;
    this.appContext = appContext;
  }

  protected void setFlashErrorMessage(fsc.response.Response response) {
    setFlashErrorMessage(((ErrorResponse) response).message);
  }

  protected void setFlashErrorMessage(String message) {
    req.session().attribute("flash", message);
  }

  String oldServeTemplate(String templatePath, HashMap<Object, Object> model) {
    Session requestSession = req.session();
    model.put("session", requestSession.attribute("session"));
    model.put("flash", requestSession.attribute("flash"));
    requestSession.removeAttribute("flash");

    return templateEngine.render(new ModelAndView(model, templatePath));
  }

  protected String serveTemplate(String templatePath) {
    modelSet("basePath", getRelativeBasePath());
    Session requestSession = req.session();
    ViewableSession session = requestSession.attribute("session");
    modelSet("session", session);
    if (session != null) {
      modelSet("isAdmin", session.role.equals("ROLE_ADMIN"));
    }
    modelSet("flash", requestSession.attribute("flash"));
    requestSession.removeAttribute("flash");

    return templateEngine.render(new ModelAndView(model, templatePath));
  }

  private String getRelativeBasePath() {
    return req.pathInfo()
                            .replaceAll("\\w+", "..")
                            .replaceFirst("/..", "")
                            .replaceFirst("/", "");
  }

  protected void loadSessionAndUserProfile() {
    loadSession();
    modelSet("session", session);
    modelSet("profile", requireExistingSessionProfile(session));
  }

  protected void loadSession() {
    session = req.session().attribute("session");
    if (session == null) {
      throw new RequireAuthenticationException();
    }
  }

  protected Object redirect(String path) {
    res.redirect(path);
    return null;
  }

  protected Object redirectWithFlash(String path, fsc.response.Response response) {
    setFlashErrorMessage(response);
    return redirect(path);
  }

  protected Object redirectWithFlash(String path, String message) {
    setFlashErrorMessage(message);
    return redirect(path);
  }

  private ViewableProfile requireExistingSessionProfile(ViewableSession session) {
    fsc.response.Response response = appContext.getProfile(session.username, session.token);
    if (response.isSuccessful()) return response.getValues();
    throw new FailedRequestException((ErrorResponse) response, Path.login());
  }

  protected void modelSet(String key, Object elections) {
    model.put(key, elections);
  }

  protected String getRequestParameter(String key) {
    if (req.queryMap().hasKey(key)) return req.queryParams(key);
    if (req.params().containsKey(":" + key)) return req.params(":" + key);
    throw new RuntimeException("Requested parameter not present: " + key);
  }

  protected void storeSession(ViewableSession createdSession) {
    req.session().attribute("session", createdSession);
  }

  protected void invalidateCurrentSession() {
    req.session().removeAttribute("session");
    req.session().invalidate();
  }

  protected void requireSessionAndAdminRole() {
    loadSession();
    if (!session.role.equals("ROLE_ADMIN")) {
      throw new RequireAuthenticationException();
    }
  }

  protected void setTypeToJSON() {
    res.type("application/json");
  }

  protected void setStatusCode(int statusCode) {
    res.status(statusCode);
  }

  protected class RequireAuthenticationException extends RuntimeException {}

  public class FailedRequestException extends RuntimeException {
    private String message;
    private String redirectPath;

    public FailedRequestException(ErrorResponse response, String redirectPath) {
      this.message = response.message;
      this.redirectPath = redirectPath;
    }

    public String getMessage() {
      return message;
    }

    public String getRedirectPath() {
      return redirectPath;
    }
  }
}
