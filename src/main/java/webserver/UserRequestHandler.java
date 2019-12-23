package webserver;

import fsc.app.AppContext;
import fsc.entity.Candidate;
import fsc.viewable.ViewableCandidate;
import fsc.viewable.ViewableElection;
import fsc.viewable.ViewableVoter;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserRequestHandler extends RequestHandler {

  public UserRequestHandler(Request req, Response res, AppContext appContext) {
    super(req, res, appContext);
  }

  public Object processGetIndex() {
    loadSessionAndUserProfile();
    Collection<ViewableElection> elections = appContext.getActiveElections(session.token)
                                                       .getValues();

    modelSet("elections", personalize(elections));
    return serveTemplate("userIndex.handlebars");
  }

  private Collection<PersonalizedElection> personalize(Collection<ViewableElection> elections) {
    return elections.stream().map(this::personalize).collect(Collectors.toList());
  }

  private PersonalizedElection personalize(ViewableElection election) {
    String username = session.username;
    Candidate.Status candidateStatus = getCandidateStatusMatching(election, username);
    ViewableVoter voter = getVoterMatching(election, username);
    return new PersonalizedElection(election, candidateStatus, voter);
  }

  private ViewableVoter getVoterMatching(ViewableElection election, String username) {
    for (ViewableVoter voter : election.voters) {
      if (voter.profile.username.equals(username)) {
        return voter;
      }
    }
    return null;
  }

  private Candidate.Status getCandidateStatusMatching(ViewableElection election, String username) {
    for (ViewableCandidate candidate : election.candidates) {
      if (candidate.profile.username.equals(username)) {
        return candidate.status;
      }
    }
    return null;
  }

  private class PersonalizedElection {
    private final ViewableElection election;
    private final String candidateStatus;
    private final ViewableVoter voter;

    public PersonalizedElection(
          ViewableElection election, Candidate.Status candidateStatus, ViewableVoter voter
    ) {
      this.election = election;
      this.candidateStatus = String.valueOf(candidateStatus);
      this.voter = voter;
    }

    public ViewableElection getElection() {
      return election;
    }

    public String getCandidateStatus() {
      return candidateStatus;
    }

    public ViewableVoter getVoter() {
      return voter;
    }
  }
}
