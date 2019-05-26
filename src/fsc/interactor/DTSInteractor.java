package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Candidate;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class DTSInteractor {

  private Gateway gateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public DTSInteractor(Gateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(DTSRequest request) {
    try {
      getProperCandidate(request);
      if (request.status == Candidate.Status.Accepted) {
        candidate.setAccepted();
      } else {
        candidate.setDeclinded();
      }
      gateway.save();
      return new SuccessResponse();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (BallotGateway.InvalidBallotIDException e) {
      return ErrorResponse.unknownBallotID();
    }
  }

  private void getProperCandidate(DTSRequest request)
        throws ProfileGateway.InvalidProfileUsernameException,
               BallotGateway.InvalidBallotIDException {
    Profile profile = gateway.getProfile(request.userName);
    Ballot ballot = gateway.getBallot(request.electionID);
    int candidateListSize = ballot.sizeCandidates();
    for (int candidateIndex = 0; candidateIndex < candidateListSize; candidateIndex++) {
      if (ballot.getCandidate(candidateIndex).getProfile() == profile) {
        candidate = ballot.getCandidate(candidateIndex);
      }
    }
  }
}