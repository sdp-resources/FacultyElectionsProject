package webserver;

import fsc.app.AppContext;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

public class ElectionRequestHandler extends RequestHandler {
  public ElectionRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processPostDecideToStand() {
    loadSessionAndUserProfile();
    String decideToStand = getRequestParameter("decideToStand");
    submitDecisionToStand(getRequestParameterLong("electionid"),
                          decideToStand);

    String message = decideToStand.equals("Willing")
                     ? "You elected to stand"
                     : "You declined to stand";
    return redirectWithFlash(Path.user(), message);
  }

  private void submitDecisionToStand(Long electionId, String decision) {
    appContext.setDTS(electionId, session.username, decision, session.token)
              .getValues(onErrorRedirectTo(Path.user()));
  }

  public Object processGetBallot() {
    loadSessionAndUserProfile();

    Long electionId = getRequestParameterLong("electionid");
    modelSet("candidates", getCandidates(electionId));
    modelSet("electionId", electionId);
    modelSet("voterId", getRequestParameter("voterId"));

    return serveTemplate("/viewBallot.handlebars");
  }

  private Object getCandidates(Long electionId) {
    return appContext.viewBallot(electionId, session.token)
                     .getValues(onErrorRedirectTo(Path.user()));
  }

  public Object processPostBallot() {
    loadSessionAndUserProfile();
    submitVote(getRequestParameterLong("voterId"),
               Arrays.asList(getRequestParameter("votes").split(",")));

    return redirectWithFlash(Path.user(), "Your vote was recorded!");
  }

  private void submitVote(Long voterId, List<String> votes) {
    appContext.submitVote(voterId, session.username, votes, session.token)
              .getValues(onErrorRedirectTo(Path.user()));
  }
}
