package fsc.interactor.election;

import fsc.entity.*;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.SubmitVoteRecordRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static fsc.entity.VoteRecordTest.assertCloseDates;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SubmitVoteRecordTest extends ElectionTest {
  public static final int VOTER_ID = 3;
  private List<String> vote;
  private SubmitVoteRecordRequest request;
  private ProvidedElectionGatewaySpy electionGateway;
  private Profile profile;
  private ProfileGateway profileGateway;
  private Election election;
  private Profile candidate;
  private ElectionInteractor interactor;
  private Voter voter;

  @Before
  public void setup() {
    election = EntityStub.simpleElectionWithCandidates();
    profile = EntityStub.getProfile(0);
    voter = new Voter(profile, election);
    voter.setVoterId(VOTER_ID);
    election.addVoter(voter);
    candidate = EntityStub.getProfile(1);
    vote = List.of(candidate.getUsername());
    request = new SubmitVoteRecordRequest(VOTER_ID, vote);
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(candidate, profile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
  }

  @Test
  public void whenGivenAnInvalidVoter_returnErrorResponse() {
    election.removeVoter(voter);
    interactor = new ElectionInteractor(electionGateway, null, new InvalidProfileGatewaySpy(), entityFactory);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.invalidVoter(), response);
  }

  @Test
  public void whenGivenVoteForNonCandidate_returnErrorResponse() {
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.invalidCandidate(), response);
    assertEquals(false, voter.hasVoted());
  }

  @Test
  public void whenGivenMultipleVotesForCandidate_returnErrorResponse() {
    election.getCandidates().add(entityFactory.createCandidate(candidate,
                                                               election));
    election.getCandidates().add(entityFactory.createCandidate(profile,
                                                               election));
    vote = List.of(candidate.getUsername(), profile.getUsername(), candidate.getUsername());
    request = new SubmitVoteRecordRequest(VOTER_ID, vote);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.multipleRanksForCandidate(), response);
    assertEquals(false, voter.hasVoted());
  }

  @Test
  public void whenGivenSecondVoteFromSameVoter_returnErrorResponse() {
    election.getCandidates().add(entityFactory.createCandidate(candidate,
                                                               election));
    interactor.execute(request);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.alreadyVoted(), response);
  }

  @Test
  public void whenGivenAVoterProfileNotInVotesList_preventThemFromVoting() {
    election.removeVoter(voter);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.invalidVoter(), response);
    assertEquals(false, electionGateway.hasSaved);
  }

  @Test
  public void whenGivenCorrectInformation_saveRecord() {
    election.getCandidates().add(entityFactory.createCandidate(candidate,
                                                               election));
    Response response = interactor.execute(request);
    VoteRecord submittedVoteRecord = electionGateway.submittedVoteRecord;
    assertNotNull(submittedVoteRecord);
    assertEquals(ResponseFactory.ofVoteRecord(submittedVoteRecord), response);
    assertCloseDates(LocalDateTime.now(), submittedVoteRecord.getDate());
    assertEquals(List.of(candidate), submittedVoteRecord.getVotes());
    assertEquals(election, submittedVoteRecord.getElection());
    assertEquals(voter, election.getVoter(profile));
    assertTrue(electionGateway.hasSaved);
    assertEquals(true, voter.hasVoted());
  }

}
