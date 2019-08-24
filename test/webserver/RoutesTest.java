package webserver;

import fsc.entity.Election;
import fsc.entity.Voter;
import fsc.response.ErrorResponse;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RoutesTest extends ServerTest {

  @Test
  public void baseLinkRedirectsToLoginForNotLoggedInUsers() {
    WebClient.getWithoutRedirect(Path.root())
             .assertRedirectsTo(Path.login());
  }

  @Test
  public void canReachLoginPage() {
    WebClient.get(Path.login())
             .assertResponseCodeIs(200)
             .assertMatch(Matcher.attribute("input", "name", "username"))
             .assertMatch(Matcher.attribute("input", "name", "password"))
             .assertNoMatch(Matcher.attribute("input", "name", "somethingrandom"));
  }

  @Test
  public void onIncorrectLoginCredentialsGetRedirectedToLogin() {
    getLoginPostClient("skiadas", "something", false)
          .assertRedirectsTo(Path.login());
  }

  @Test
  public void redirectedInvalidLoginPageContainsFlashMessage() {
    getLoginPostClient("skiadas", "something", true)
          .assertResponseCodeIs(200)
          .assertMatch(".alert");
  }

  @Test
  public void correctLoginCredentialsGetRedirectedToIndexThenUser() {
    getLoginPostClient("skiadas", "apassword", false)
          .assertRedirectsTo(Path.root())
          .followGet(Path.root())
          .assertRedirectsTo(Path.user());

    getLoginPostClient("admin", "adminpass", false)
          .assertRedirectsTo(Path.root())
          .followGet(Path.root())
          .assertRedirectsTo(Path.admin());
  }

  @Test
  public void correctLoginCredentialsRedirectToLoggedInPerson() {
    normalLoggedInClient()
          .assertResponseCodeIs(200)
          .assertMatch(Matcher.attribute("a", "href", Path.logout()));
  }

  @Test
  public void loggedInUserCanStartAtUserPage() {
    getLoginPostClient("skiadas", "apassword", false)
          .assertRedirectsTo(Path.root())
          .followGet(Path.user())
          .assertResponseCodeIs(200)
          .assertMatch(Matcher.attribute("a", "href", Path.logout()));
  }

  @Test
  public void nonLoggedInUserGoingStraightToUserPageShouldRedirect() {
    WebClient.getWithoutRedirect(Path.user())
             .assertRedirectsTo(Path.login());
  }

  @Test
  public void loggingOutRedirectsToLoginPageAndLogsUserOut() {
    normalLoggedInClient()
          .followGet(Path.logout()).assertRedirectsTo(Path.login())
          .followGet(Path.root()).assertRedirectsTo(Path.login());
  }

  @Test
  public void loggedInUserCanDecideToStandAndVote() {
    WebClient client = normalLoggedInClient();
    for (Election election : router.getGateway().getAllElections()) {
      checkThatCandidateCanSeeDTSLink(client, election);
      checkThatVoterCanSeeBallotLink(client, election);
    }
  }

  @Test
  public void loggedInSessionIsPreserved() {
    normalLoggedInClient()
          .followGet(Path.user()).assertResponseCodeIs(200)
          .followGet(Path.user()).assertResponseCodeIs(200);
  }

  @Test
  public void loggedInUserCanDecideToStand() {
    WebClient client = normalLoggedInClient();
    for (Election election : router.getGateway().getAllElections()) {
      if (election.isInDecideToStandState() && election.hasCandidate("skiadas")) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("decideToStand", "Willing");
        client.followPost(Path.decideToStand(election), parameters)
              .assertRedirectsTo(Path.user())
              .followGet(Path.user())
              .assertResponseCodeIs(200)
              .assertMatch(Matcher.contains("Candidate status: Willing"));
      }
    }
  }

  @Test
  public void loggedInUserCanFollowBallotLink() {
    WebClient client = normalLoggedInClient();
    for (Election election : router.getGateway().getAllElections()) {
      if (election.isInVoteState()) {
        for (Voter voter : election.getVoters()) {
          if (voter.getProfile().getUsername().equals("skiadas") && !voter.hasVoted()) {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("voterId", String.valueOf(voter.getVoterId()));
            String ballotLink = Path.ballot(election.getID());
            client.follow(ballotLink, "GET", parameters, false)
                  .assertResponseCodeIs(200)
                  .assertMatch(Matcher.contains("Charilaos Skiadas"))
                  .assertMatch(Matcher.contains("Theresa Wilson"))
                  .assertMatch(Matcher.contains("Kate Johnson"));
            parameters.put("votes", "wilsont,skiadas");
            client.followPost(ballotLink, parameters)
                  .assertRedirectsTo(Path.user())
                  .followGet(Path.user())
                  .assertNoMatch(Matcher.attribute("form ", "action", ballotLink))
                  .followPost(ballotLink, parameters)
                  .assertRedirectsTo(Path.user())
                  .followGet(Path.user())
                  .assertMatch(Matcher.contains(ErrorResponse.VOTER_ALREADY_VOTED));
          }
        }
      }
    }

  }

  private void checkThatVoterCanSeeBallotLink(WebClient client, Election election) {
    if (election.isInVoteState()) {
      for (Voter voter : election.getVoters()) {
        if (voter.getProfile().getUsername().equals("skiadas")) {
          String formLink = Matcher.attribute("form", "action", Path.ballot(election.getID()));
          if (voter.hasVoted()) {
            client.assertNoMatch(formLink);
          } else {
            client.assertMatch(formLink);
          }
        }
      }

    }
  }

  private void checkThatCandidateCanSeeDTSLink(WebClient client, Election election) {
    if (election.isInDecideToStandState() && election.hasCandidate("skiadas")) {
      client.assertMatch(
            Matcher.attribute("form", "action", Path.decideToStand(election.getID())));
    }
  }

  @Test
  public void adminMainPageHasLinksToProfilesCommitteesAndElectionsPages() {
    getLoginPostClient("admin", "adminpass", false)
          .assertRedirectsTo(Path.root())
          .followGet(Path.root())
          .assertRedirectsTo(Path.admin())
          .followGet(Path.admin())
          .assertResponseCodeIs(200);
    adminLoggedInClient()
          .assertMatch(Matcher.attribute("", "href", Path.adminProfile()))
          .assertMatch(Matcher.attribute("", "href", Path.adminElection()))
          .assertMatch(Matcher.attribute("", "href", Path.adminCommittee()));
  }

  @Test
  public void adminProfilePageHasAllProfiles() {
    adminLoggedInClient()
          .followGet(Path.adminProfile())
          .assertMatch(Matcher.contains("Charilaos Skiadas"))
          .assertMatch(Matcher.contains("Theresa Wilson"));
  }

  @Test
  public void adminCommitteePageContainsAllCommittees() {
    adminLoggedInClient()
          .followGet(Path.adminCommittee())
          .assertMatch(Matcher.contains("Faculty Evaluation Committee"))
          .assertMatch(Matcher.contains("Faculty Steering Committee"));
  }

  @Test
  public void queryValidationReturnsNormalizedAndExpandedOnValidRequests() {
    assertValidInputProduces("isActive",
                             "isActive",
                             "status equals \"active\"");
    assertValidInputProduces("isActive and notChaplain",
                             "(isActive and notChaplain)",
                             "(status equals \"active\" and " +
                                   "(not contract equals \"chaplain\"))");
    assertValidInputProduces("isTenured or isUntenured",
                             "(isTenured or isUntenured)",
                             "(contract equals \"tenured\" or contract equals \"untenured\")");
  }

  @Test
  public void queryValidationReturnsErrorMessageOnInvalidForms() {
    assertInvalidInputReturnsMessageContaining("isSomething",
                                               "isSomething");
    assertInvalidInputReturnsMessageContaining("isTenured and",
                                               "Expected");
  }

  private void assertInvalidInputReturnsMessageContaining(String input, String output) {
    queryValidationRequestClient(input)
          .assertResponseCodeIs(404)
          .assertServedTypeIsJSON()
          .assertContainsKeyMatching("error", "Error")
          .assertContainsKeyMatching("error", output);
  }

  private void assertValidInputProduces(String input, String output, String expandedOutput) {
    queryValidationRequestClient(input)
          .assertResponseCodeIs(200)
          .assertServedTypeIsJSON()
          .assertContainsKey("string", output)
          .assertContainsKey("expandedString", expandedOutput);
  }

  private WebClient normalLoggedInClient() {
    return getLoginPostClient("skiadas", "apassword", true);
  }

  private WebClient adminLoggedInClient() {
    return getLoginPostClient("admin", "adminpass", true);
  }

  private WebClient getLoginPostClient(String username, String apassword, boolean followRedirects) {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("username", username);
    parameters.put("password", apassword);
    return WebClient.post(Path.login(), parameters, followRedirects);
  }

  private JSONClient queryValidationRequestClient(String queryString) {
    Map<String, String> params = new HashMap<>();
    params.put("query", queryString);
    return new JSONClient(Path.validate(), "GET", params);
  }
}
