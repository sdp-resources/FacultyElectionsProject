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
import java.util.stream.Collectors;

import static fsc.entity.VoteRecordTest.assertCloseDates;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

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
    election.setState(State.Vote);
    profile = EntityStub.getProfile(0);
    voter = new Voter(profile, election);
    voter.setVoterId(VOTER_ID);
    election.addVoter(voter);
    candidate = EntityStub.getProfile(1);
    vote = List.of(candidate.getUsername());
    request = new SubmitVoteRecordRequest(VOTER_ID, profile.getUsername(), vote);
    request.setSession(EntityStub.userSession(profile.getUsername()));
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(candidate, profile);
    interactor = new ElectionInteractor(electionGateway, null, profileGateway, entityFactory);
  }

  @Test
  public void whenGivenAnInvalidVoter_returnErrorResponse() {
    election.removeVoter(voter);
    interactor = new ElectionInteractor(electionGateway, null, new InvalidProfileGatewaySpy(),
                                        entityFactory);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidVoter(), response);
  }

  @Test
  public void whenGivenVoteForNonCandidate_returnErrorResponse() {
    addCandidate(election, profile);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidCandidate(), response);
    assertEquals(false, voter.hasVoted());
  }

  @Test
  public void whenGivenMultipleVotesForCandidate_returnErrorResponse() {
    addCandidate(election, candidate);
    addCandidate(election, profile);
    vote = List.of(candidate.getUsername(), profile.getUsername(), candidate.getUsername());
    request = new SubmitVoteRecordRequest(VOTER_ID, profile.getUsername(), vote);
    request.setSession(EntityStub.userSession(profile.getUsername()));
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.multipleRanksForCandidate(), response);
    assertEquals(false, voter.hasVoted());
  }

  @Test
  public void whenGivenSecondVoteFromSameVoter_returnErrorResponse() {
    addCandidate(election, candidate);
    interactor.handle(request);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.alreadyVoted(), response);
  }

  @Test
  public void whenGivenAVoterProfileNotInVotesList_preventThemFromVoting() {
    election.removeVoter(voter);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidVoter(), response);
    assertEquals(false, electionGateway.hasSaved);
  }

  @Test
  public void whenElectionIsNotInVoteMode_returnError() {
    addCandidate(election, candidate);
    election.setState(State.Setup);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertEquals(false, electionGateway.hasSaved);
    assertEquals(false, voter.hasVoted());
    election.setState(State.DecideToStand);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertEquals(false, electionGateway.hasSaved);
    assertEquals(false, voter.hasVoted());
    election.setState(State.Closed);
    assertEquals(ResponseFactory.improperElectionState(), interactor.handle(request));
    assertEquals(false, electionGateway.hasSaved);
    assertEquals(false, voter.hasVoted());
  }

  @Test
  public void whenGivenCorrectInformation_saveRecord() {
    addCandidate(election, candidate);
    Response response = interactor.handle(request);
    VoteRecord submittedVoteRecord = electionGateway.submittedVoteRecord;
    assertNotNull(submittedVoteRecord);
    assertEquals(ResponseFactory.ofVoteRecord(submittedVoteRecord), response);
    assertCloseDates(LocalDateTime.now(), submittedVoteRecord.getDate());
    List<Profile> candidateProfiles = List.of(this.candidate);
    List<String> candidateUsernames = candidateProfiles.stream()
                                                       .map(Profile::getUsername)
                                                       .collect(Collectors.toList());

    assertEquals(candidateUsernames, submittedVoteRecord.getVotes());
    assertEquals(election, submittedVoteRecord.getElection());
    assertEquals(voter, election.getVoter(profile));
    assertTrue(electionGateway.hasSaved);
    assertEquals(true, voter.hasVoted());
  }

  private void addCandidate(Election election, Profile profile) {
    election.getCandidates()
            .add(entityFactory.createCandidate(profile, election));
  }
}
