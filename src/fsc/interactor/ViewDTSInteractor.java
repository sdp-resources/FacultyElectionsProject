package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Candidate;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway;
import fsc.request.ViewDTSRequest;
import fsc.response.*;

public class ViewDTSInteractor {

  public Gateway gateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public ViewDTSInteractor(Gateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewDTSRequest request) {
    try {
      getProperCandidate(request);
      return new ViewDTSResponse(candidate);
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (BallotGateway.InvalidBallotIDException e) {
      return ErrorResponse.unknownBallotID();
    }
  }

  private void getProperCandidate(ViewDTSRequest request)
        throws ProfileGateway.InvalidProfileUsernameException,
               BallotGateway.InvalidBallotIDException {
    Profile profile = gateway.getProfile(request.username);
    Ballot ballot = gateway.getBallot(request.electionID);
    int candidateListSize = ballot.sizeCandidates();
    for (int candidateIndex = 0; candidateIndex < candidateListSize; candidateIndex++) {
      if (ballot.getCandidate(candidateIndex).getProfile() == profile) {
        candidate = ballot.getCandidate(candidateIndex);
      }
    }
  }
}
