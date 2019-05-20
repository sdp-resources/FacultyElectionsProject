package fsc.request;

import fsc.entity.Election;

public class ViewCandidatesRequest {
  public String electionID;

  public ViewCandidatesRequest(String electionID) {
    this.electionID = electionID;
  }
}
