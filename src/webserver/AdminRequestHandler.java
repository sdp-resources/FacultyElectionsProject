package webserver;

import fsc.app.AppContext;
import spark.Request;
import spark.Response;

public class AdminRequestHandler extends RequestHandler {
  public AdminRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processGetIndex() {
    requireSessionAndAdminRole();
    return serveTemplate("adminIndex.handlebars");
  }

  public Object processGetProfiles() {
    requireSessionAndAdminRole();
    fsc.response.Response response = appContext.getProfilesMatchingQuery("all", session.token);
    modelSet("profiles", response.getValues());
    return serveTemplate("adminProfileList.handlebars");
  }
}
