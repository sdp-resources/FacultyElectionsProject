package fsc.interactor;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.VoteRecordRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static fsc.entity.VoteRecordTest.assertCloseDates;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CastVoteInteractorTest {
  public static final String ELECTION_ID = "1";
  private Date date;
  private List<String> vote;
  private VoteRecordRequest request;
  private ProvidedElectionGatewaySpy electionGateway;
  private Profile voter;
  private ProfileGateway profileGateway;
  private Election election;
  private Profile candidate;
  private CastVoteInteractor interactor;

  @Before
  public void setup() {
    election = EntityStub.simpleBallotElection();
    election.setID(ELECTION_ID);
    voter = EntityStub.getProfile(0);
    candidate = EntityStub.getProfile(1);
    vote = List.of(candidate.getUsername());
    request = new VoteRecordRequest(voter.getUsername(), vote, ELECTION_ID);
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(candidate, voter);
  }

  @Test
  public void whenGivenAnInvalidProfile_returnErrorResponse() {
    interactor = new CastVoteInteractor(electionGateway, new InvalidProfileGatewaySpy());
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void whenGivenAnInvalidElectionId_returnErrorResponse() {
    interactor = new CastVoteInteractor(new RejectingElectionGatewaySpy(), profileGateway);
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.unknownElectionID(), response);
  }

  @Test
  public void whenGivenVoteForNonCandidate_returnErrorResponse() {
    interactor = new CastVoteInteractor(electionGateway, profileGateway);
    election.getBallot().addCandidate(voter);
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.invalidCandidate(), response);
  }

  @Test
  public void whenGivenMultipleVotesForCandidate_returnErrorResponse() {
    interactor = new CastVoteInteractor(electionGateway, profileGateway);
    election.getBallot().addCandidate(candidate);
    election.getBallot().addCandidate(voter);
    vote = List.of(candidate.getUsername(), voter.getUsername(), candidate.getUsername());
    request = new VoteRecordRequest(voter.getUsername(), vote, ELECTION_ID);
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.multipleRanksForCandidate(), response);
  }

  @Test
  public void whenGivenSecondVoteFromSameVoter_returnErrorResponse() {
    interactor = new CastVoteInteractor(electionGateway, profileGateway);
    election.getBallot().addCandidate(candidate);
    interactor.execute(request);
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.alreadyVoted(), response);
  }

  @Test
  public void whenGivenCorrectInformation_saveRecord() {
    interactor = new CastVoteInteractor(electionGateway, profileGateway);
    election.getBallot().addCandidate(candidate);
    Response response = interactor.execute(request);
    assertEquals(new SuccessResponse(), response);
    VoteRecord submittedVoteRecord = electionGateway.submittedVoteRecord;
    assertNotNull(submittedVoteRecord);
    assertEquals(voter, submittedVoteRecord.getVoter());
    assertCloseDates(Calendar.getInstance().getTime(), submittedVoteRecord.getDate());
    assertEquals(List.of(candidate), submittedVoteRecord.getVotes());
    assertEquals(election, submittedVoteRecord.getElection());
    assertTrue(electionGateway.hasSaved);
  }

}
