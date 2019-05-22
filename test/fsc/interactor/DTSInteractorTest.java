package fsc.interactor;

import fsc.entity.Candidate;
import fsc.mock.NoElectionExistsDTSGatewaySpy;
import fsc.request.DTSRequest;
import org.junit.Ignore;
import org.junit.Test;

import static fsc.entity.Candidate.Status.Declined;
import static org.junit.Assert.assertEquals;

public class DTSInteractorTest {

  private String elecionID = "12345";
  private String profileUsername = "someUsername";
  private Candidate.Status status = Declined;

  @Ignore
  public void correctExecuteTest(){
    NoElectionExistsDTSGatewaySpy gateway = new NoElectionExistsDTSGatewaySpy();
    DTSRequest request = new DTSRequest(elecionID, profileUsername, status);
    assertEquals(request.electionID,gateway.submittedID);
    assertEquals(request.userName,gateway.submittedUserName);
    assertEquals(request.status,gateway.submittedStatus);
  }
}

