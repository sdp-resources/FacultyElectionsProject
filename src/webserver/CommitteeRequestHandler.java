package webserver;

import fsc.app.AppContext;
import spark.Request;
import spark.Response;

public class CommitteeRequestHandler extends RequestHandler {
  public CommitteeRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processGetAdminCommittee() {
    requireSessionAndAdminRole();
    modelSet("committees", appContext.getAllCommittees(session.token).getValues());
    return serveTemplate("/committeeList.handlebars");

  }
}
