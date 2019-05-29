package fsc.interactor;

import fsc.entity.Candidate;
import fsc.entity.Election;
import fsc.mock.EntityStub;
import fsc.mock.ViewDTSGatewayDummy;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SubmitDTSInteractorTest {

  private final String electionID = "12345";
  private final String profileUserName = "skiadas21";
  private final Candidate.Status status = Candidate.Status.Declined;
  private Election election = EntityStub.simpleBallotElection();

  private ProvidedElectionGatewaySpy electionGatewaySpy;

  @Ignore
  @Test
  public void whenCandidateIsInElection_thenRecordTheirStatus() {
    DTSRequest request = new DTSRequest(electionID, profileUserName, status);
    electionGatewaySpy = new ProvidedElectionGatewaySpy(election);
    DTSInteractor interactor = new DTSInteractor(electionGatewaySpy);
    Response response = interactor.execute(request);

    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void whenCandidateIsNotInElection_thenError() {
    DTSRequest request = new DTSRequest(electionID, "wilson", status);
    ViewDTSGatewayDummy gateway = new ViewDTSGatewayDummy();
    DTSInteractor interactor = new DTSInteractor(gateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof ErrorResponse);
  }
}

