package fsc.response;

import fsc.entity.Candidate;
import fsc.viewable.ViewableProfile;

public class ViewDTSResponse implements Response {

  public ViewableProfile profile;
  public String Status;

  public ViewDTSResponse(Candidate candidate){
    this.profile = new ViewableProfile(candidate.getProfile().getName(),
                                       candidate.getProfile().getUsername(),
                                       candidate.getProfile().getDivision(),
                                       candidate.getProfile().getContract());
    this.Status = candidate.getStatus().toString();
  }

}
