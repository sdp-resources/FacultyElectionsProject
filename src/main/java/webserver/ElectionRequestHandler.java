package webserver;

import fsc.app.AppContext;
import fsc.entity.State;
import fsc.response.Response;
import fsc.viewable.ViewableElection;
import fsc.viewable.ViewableProfile;
import fsc.voting.ElectionRecord;
import spark.Request;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElectionRequestHandler extends RequestHandler {
  public ElectionRequestHandler(Request req, spark.Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  Object showElections() {
    requireSessionAndAdminRole();
    Collection<ViewableElection> values = appContext.getAllElections(session.token).getValues();
    modelSet("elections", values);
    return serveTemplate("/electionList.handlebars");
  }

  public Object createElection() {
    requireSessionAndAdminRole();
    Long seatId = getRequestParameterLong("seatId");
    Response<ViewableElection> response = appContext.createElection(seatId, session.token);
    ViewableElection election = response.getValues();
    String path = Path.adminElection(election.electionID);
    redirect(path);
    return null;
  }

  Object showElection() {
    requireSessionAndAdminRole();
    Long electionid = getRequestParameterLong("electionId");
    Response<ViewableElection> response = appContext.viewElection(electionid, session.token);
    ViewableElection election = response.getValues();
    List<ViewableProfile> allProfiles = appContext
                                              .getProfilesMatchingQuery("all", session.token)
                                              .getValues();
    modelSet("election", election);
    modelSet("states", Arrays.asList(State.values()));
    modelSet("missingCandidates", filterProfilesExcluding(allProfiles,
                                                          election.candidates,
                                                          c -> c.profile));
    modelSet("missingVoters",
             filterProfilesExcluding(allProfiles, election.voters, c -> c.profile));
    modelSet("results", getElectionResults(election.electionID));
    return serveTemplate("/adminElection.handlebars");
  }

  private ElectionRecord getElectionResults(long electionID) {
    Response<ElectionRecord> electionResults = appContext.getElectionResults(electionID,
                                                                             session.token);
    return electionResults.getValues();
  }

  private <T> List<ViewableProfile> filterProfilesExcluding(
        List<ViewableProfile> allProfiles,
        Collection<T> voters,
        Function<T, ViewableProfile> mapper
  ) {
    List<ViewableProfile> existingProfiles = voters.stream().map(mapper)
                                                   .collect(Collectors.toList());
    return allProfiles.stream().filter(o -> !existingProfiles.contains(o))
                      .collect(Collectors.toList());
  }

  public Object changeElectionState() {
    requireSessionAndAdminRole();
    Long electionid = getRequestParameterLong("electionId");
    String newState = getRequestParameter("state");
    Response response = appContext.setElectionState(electionid, newState, session.token);
    if (response.isSuccessful()) {
      return redirectWithFlash(Path.adminElection(electionid), "State changed to: " + newState);
    }
    return redirectWithFlash(Path.adminElection(electionid), response);
  }

  public Object deleteVoter() {
    requireSessionAndAdminRole();
    Long electionid = getRequestParameterLong("electionId");
    String username = getRequestParameter("username");
    Response response = appContext.removeVoter(electionid, username, session.token);
    if (response.isSuccessful()) {
      return redirectWithFlash(Path.adminElection(electionid) + "#voters", "Voter " + username + " removed");
    }
    return redirectWithFlash(Path.adminElection(electionid) + "#voters", response);
  }

  public Object addVoter() {
    requireSessionAndAdminRole();
    Long electionid = getRequestParameterLong("electionId");
    String username = getRequestParameter("username");
    Response response = appContext.addVoter(electionid, username, session.token);
    if (response.isSuccessful()) {
      return redirectWithFlash(Path.adminElection(electionid) + "#voters", "Voter " + username + " added");
    }
    return redirectWithFlash(Path.adminElection(electionid) + "#voters", response);
  }

  public Object deleteCandidate() {
    requireSessionAndAdminRole();
    Long electionid = getRequestParameterLong("electionId");
    String username = getRequestParameter("username");
    Response response = appContext.removeCandidate(electionid, username, session.token);
    if (response.isSuccessful()) {
      return redirectWithFlash(Path.adminElection(electionid), "Candidate " + username + " " +
                                                                     "removed");
    }
    return redirectWithFlash(Path.adminElection(electionid), response);
  }

  public Object addCandidate() {
    requireSessionAndAdminRole();
    Long electionid = getRequestParameterLong("electionId");
    String username = getRequestParameter("username");
    Response response = appContext.addCandidate(electionid, username, session.token);
    if (response.isSuccessful()) {
      return redirectWithFlash(Path.adminElection(electionid), "Candidate " + username + " added");
    }
    return redirectWithFlash(Path.adminElection(electionid), response);
  }

  public Object processPostDecideToStand() {
    loadSessionAndUserProfile();
    String decideToStand = getRequestParameter("decideToStand");
    submitDecisionToStand(getRequestParameterLong("electionId"),
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

    Long electionId = getRequestParameterLong("electionId");
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
