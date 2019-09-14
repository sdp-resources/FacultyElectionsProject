package webserver;

import fsc.entity.Election;
import fsc.entity.Voter;

import java.util.HashMap;
import java.util.Map;

public class WebClients {
  static WebClient normalLoggedInClient() {
    return loggedInClient("skiadas", "apassword");
  }

  static WebClient adminLoggedInClient() {
    return loggedInClient("admin", "adminpass");
  }

  private static WebClient loggedInClient(String skiadas, String apassword) {
    return getLoginPostClient(skiadas, apassword, true);
  }

  static WebClient getLoginPostClient(
        String username, String apassword, boolean followRedirects
  ) {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("username", username);
    parameters.put("password", apassword);
    return WebClient.post(Path.login(), parameters, followRedirects);
  }

  static WebClient getActiveElectionClient() {
    for (Election election : ServerTest.router.getGateway().getAllElections()) {
      if (election.isInVoteState()) {
        for (Voter voter : election.getVoters()) {
          if (voter.getProfile().getUsername().equals("wilsont") && !voter.hasVoted()) {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("voterId", String.valueOf(voter.getVoterId()));
            String ballotLink = Path.ballot(election.getID());
            return loggedInClient("wilsont", "apassword")
                         .follow(ballotLink, "GET", parameters, false);
          }
        }
      }
    }
    throw new RuntimeException("No election present where test profile can vote");
  }
}