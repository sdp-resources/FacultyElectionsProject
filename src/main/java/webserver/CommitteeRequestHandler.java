package webserver;

import fsc.app.AppContext;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CommitteeRequestHandler extends RequestHandler {
  public CommitteeRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processGetAdminCommittee() {
    requireSessionAndAdminRole();
    modelSet("committees", appContext.getAllCommittees(session.token).getValues());
    return serveTemplate("/committeeList.handlebars");

  }

  public Object processPostCommittee() {
    requireSessionAndAdminRole();
    appContext.editCommittee(getRequestParameter("committeeName"),
                             getChangesFromRequest(getRequestParameter("changes")),
                             session.token);
    return redirect(Path.adminAllCommittees());
  }

  private Map<String, Object> getChangesFromRequest(String changesString) {
    JSONObject changesJSON = new JSONObject(changesString);
    Map<String, Object> changes = new HashMap<>();
    for (String key : changesJSON.keySet()) {
      changes.put(key, changesJSON.get(key));
    }

    return changes;
  }
}
