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
import fsc.request.SubmitVoteRecordRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static fsc.entity.VoteRecordTest.assertCloseDates;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CastVoteInteractorTest {
  public static final String ELECTION_ID = "1";
  private List<String> vote;
  private SubmitVoteRecordRequest request;
  private ProvidedElectionGatewaySpy electionGateway;
  private Profile voter;
  private ProfileGateway profileGateway;
  private Election election;
  private Profile candidate;
  private ElectionInteractor interactor;

  @Before
  public void setup() {
    election = EntityStub.simpleBallotElection();
    election.setID(ELECTION_ID);
    voter = EntityStub.getProfile(0);
    candidate = EntityStub.getProfile(1);
    vote = List.of(candidate.getUsername());
    request = new SubmitVoteRecordRequest(voter.getUsername(), vote, ELECTION_ID);
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(candidate, voter);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway);
  }

  @Test
  public void whenGivenAnInvalidProfile_returnErrorResponse() {
    interactor = new ElectionInteractor(electionGateway, null, new InvalidProfileGatewaySpy());
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void whenGivenAnInvalidElectionId_returnErrorResponse() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null, profileGateway);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenGivenVoteForNonCandidate_returnErrorResponse() {
    election.getBallot().addCandidate(voter);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.invalidCandidate(), response);
  }

  @Test
  public void whenGivenMultipleVotesForCandidate_returnErrorResponse() {
    election.getBallot().addCandidate(candidate);
    election.getBallot().addCandidate(voter);
    vote = List.of(candidate.getUsername(), voter.getUsername(), candidate.getUsername());
    request = new SubmitVoteRecordRequest(voter.getUsername(), vote, ELECTION_ID);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.multipleRanksForCandidate(), response);
  }

  @Test
  public void whenGivenSecondVoteFromSameVoter_returnErrorResponse() {
    election.getBallot().addCandidate(candidate);
    interactor.execute(request);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.alreadyVoted(), response);
  }

  @Test
  public void whenGivenCorrectInformation_saveRecord() {
    election.getBallot().addCandidate(candidate);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.success(), response);
    VoteRecord submittedVoteRecord = electionGateway.submittedVoteRecord;
    assertNotNull(submittedVoteRecord);
    assertEquals(voter, submittedVoteRecord.getVoter());
    assertCloseDates(LocalDateTime.now(), submittedVoteRecord.getDate());
    assertEquals(List.of(candidate), submittedVoteRecord.getVotes());
    assertEquals(election, submittedVoteRecord.getElection());
    assertTrue(electionGateway.hasSaved);
  }

}
