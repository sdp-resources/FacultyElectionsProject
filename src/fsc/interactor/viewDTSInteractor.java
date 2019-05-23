package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Candidate;
import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.request.ViewDTSRequest;
import fsc.response.*;

public class viewDTSInteractor {

  public Gateway gateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public viewDTSInteractor(Gateway gateway){
    this.gateway = gateway;
  }

  public Response execute(ViewDTSRequest request) throws ErrorResponse {
    try {
      Profile profile = gateway.getProfile(request.username);
      Ballot ballot = gateway.getBallot(request.electionID);
      ballot.contains(profile);
      int candidateListSize = ballot.sizeCandidates();
      for (int candidateIndex = 0; candidateIndex < candidateListSize; candidateIndex++){
        if (ballot.getCandidate(candidateIndex).getProfile() == profile) {
          candidate = ballot.getCandidate(candidateIndex);
        }
      }
      return new viewDTSResponse(candidate);
    }
    catch (Exception e) {
      throw new ErrorResponse("Invalid request");
    }
  }
}
