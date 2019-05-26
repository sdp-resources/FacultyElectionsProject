package fsc.request;

import fsc.entity.Candidate;

public class DTSRequest {
  public final String electionID;
  public final String userName;
  public final Candidate.Status status;

  public DTSRequest(String electionID, String userName, Candidate.Status status) {
    this.electionID = electionID;
    this.status = status;
    this.userName = userName;
  }
}
