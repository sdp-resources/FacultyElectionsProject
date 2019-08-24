package webserver;

import fsc.app.AppContext;
import fsc.response.ErrorResponse;
import spark.Request;
import spark.Response;

import java.util.Arrays;

public class ElectionRequestHandler extends RequestHandler {
  public ElectionRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processPostDecideToStand() {
    loadSessionAndUserProfile();
    fsc.response.Response response = appContext.setDTS(
          Long.valueOf(getRequestParameter("electionid")),
          session.username,
          getRequestParameter("decideToStand"),
          session.token
    );
    if (!response.isSuccessful()) {
      throw new FailedRequestException((ErrorResponse) response, "/user");
    }

    return redirect("/user");
  }

  public Object processGetBallot() {
    loadSessionAndUserProfile();
    fsc.response.Response response = appContext.viewBallot(
          Long.valueOf(getRequestParameter("electionid")), session.token);

    if (!response.isSuccessful()) {
      throw new FailedRequestException((ErrorResponse) response, "/user");
    }
    modelSet("electionId", getRequestParameter("electionid"));
    modelSet("voterId", getRequestParameter("voterId"));
    modelSet("candidates", response.getValues());

    return serveTemplate("/viewBallot.handlebars");
  }

  public Object processPostBallot() {
    loadSessionAndUserProfile();
    fsc.response.Response response = appContext.submitVote(
          Long.valueOf(getRequestParameter("voterId")),
          session.username,
          Arrays.asList(getRequestParameter("votes").split(",")),
          session.token);
    if (!response.isSuccessful()) {
      throw new FailedRequestException((ErrorResponse) response, "/user");
    }

    return redirectWithFlash("/user", "Your vote was recorded!");
  }
}
