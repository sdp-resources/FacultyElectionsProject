package fsc.response;

import fsc.entity.Candidate;
import fsc.entity.Profile;

public class ViewDTSResponse implements Response {

  public Profile profile;
  public String Status;

  public ViewDTSResponse(Candidate candidate){
    this.profile = candidate.getProfile();
    this.Status = candidate.getStatus().toString();
  }

}
