package webserver;

import com.github.jknack.handlebars.helper.AssignHelper;
import fsc.app.AppContext;
import fsc.response.ErrorResponse;
import fsc.viewable.ViewableProfile;
import fsc.viewable.ViewableSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Function;

public class RequestHandler {
  private static final MyHandlebarsTemplateEngine templateEngine =
        new MyHandlebarsTemplateEngine(new File("templates"));
  private final Request req;
  private final Response res;
  protected AppContext appContext;
  protected HashMap<Object, Object> model = new HashMap<>();
  protected ViewableSession session;

  static {
    templateEngine.registerHelper("path", HandlebarsHelpers.path());
    templateEngine.registerHelper("assign", new AssignHelper());
    Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    logger.error("Running from location:" + Paths.get("").toAbsolutePath());

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
    return appContext.getProfile(session.username, session.token)
                     .getValues(onErrorRedirectTo(Path.login()));
  }

  protected void modelSet(String key, Object elections) {
    model.put(key, elections);
  }

  protected String getRequestParameter(String key) {
    if (req.queryMap().hasKey(key)) return req.queryParams(key);
    String paramKey = ":" + key.toLowerCase();
    if (req.params().containsKey(paramKey)) return req.params(
          paramKey);
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

  <T> Function<String, T> onErrorRedirectTo(String redirectPath) {
    return message -> {
      throw new FailedRequestException(message, redirectPath);
    };
  }

  protected Long getRequestParameterLong(String electionid) {
    return Long.valueOf(getRequestParameter(electionid));
  }

  protected class RequireAuthenticationException extends RuntimeException {}

  public class FailedRequestException extends RuntimeException {
    private String message;
    private String redirectPath;

    public FailedRequestException(String message, String redirectPath) {
      this.message = message;
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
