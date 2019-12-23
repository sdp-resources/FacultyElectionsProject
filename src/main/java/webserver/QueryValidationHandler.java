package webserver;

import fsc.app.AppContext;
import fsc.viewable.ViewableValidationResult;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class QueryValidationHandler extends RequestHandler {
  public QueryValidationHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processGetQueries() {
    requireSessionAndAdminRole();
    Object queries = appContext.getAllQueries(session.token)
                               .getValues(onErrorRedirectTo(Path.queryAll()));
    modelSet("queries", queries);
    return serveTemplate("namedQueries.handlebars");
  }

  public Object processEditNamedQuery() {
    requireSessionAndAdminRole();
    String name = getRequestParameter("name");
    String queryString = getRequestParameter("queryString");
    appContext.editNamedQuery(session.token, name, queryString)
              .getValues(onErrorRedirectTo(Path.queryNamed(name)));
    return redirect(Path.queryNamed(name));
  }

  public Object validateQuery() {
    setTypeToJSON();
    String queryString = getRequestParameter("query");
    fsc.response.Response<ViewableValidationResult> response = appContext.validateQueryString(
          queryString);
    ViewableValidationResult result = response.getValues();
    return result.isValid ? validResponse(result) : invalidResponse(result);
  }

  private Object invalidResponse(ViewableValidationResult result) {
    JSONObject returnValue = new JSONObject();
    returnValue.put("error", result.message);
    return returnValue.toString();
  }

  private Object validResponse(ViewableValidationResult result) {
    JSONObject returnValue = new JSONObject();
    returnValue.put("string", result.message);
    returnValue.put("expandedString", result.expandedString);
    return returnValue.toString();
  }
}
