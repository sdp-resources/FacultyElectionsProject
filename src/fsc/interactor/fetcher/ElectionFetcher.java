package fsc.interactor.fetcher;

import fsc.entity.*;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.response.ResponseFactory;
import fsc.response.builder.ResponseBuilder;

import java.util.List;

public class ElectionFetcher extends ProfileFetcher {
  private ElectionGateway electionGateway;
  private CommitteeGateway committeeGateway;

  public ElectionFetcher(
        ElectionGateway electionGateway,
        ProfileGateway profileGateway,
        CommitteeGateway committeeGateway
  ) {
    super(profileGateway);
    this.electionGateway = electionGateway;
    this.committeeGateway = committeeGateway;
  }

  public ResponseBuilder<VoteRecord> fetchRecord(Voter voter) {
    try {
      return ResponseBuilder.ofValue(electionGateway.getVoteRecord(voter));
    } catch (ElectionGateway.NoVoteRecordException e) {
      return ResponseBuilder.ofResponse(ResponseFactory.noVote());
    }
  }

  public ResponseBuilder<Election> fetchElection(String electionID) {
    try {
      return ResponseBuilder.ofValue(electionGateway.getElection(electionID));
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseBuilder.ofResponse(ResponseFactory.unknownElectionID());
    }
  }

  public ResponseBuilder<Voter> fetchVoter(String username, String electionId) {
    return fetchProfile(username)
                 .bindWith(fetchElection(electionId),
                           ResponseBuilder.lift(Voter::new));

  }

  public ResponseBuilder<Voter> fetchVoterOnlyIfNoRecord(String username, String electionID) {
    return fetchVoter(username, electionID)
                 .escapeIf(electionGateway::hasVoteRecord,
                           ResponseFactory.alreadyVoted());
  }

  public ResponseBuilder<Election> removeProfile(Election election, Profile profile) {
    try {
      election.getBallot().remove(profile);
      return ResponseBuilder.ofValue(election);
    } catch (Ballot.NoProfileInBallotException e) {
      return ResponseBuilder.ofResponse(ResponseFactory.invalidCandidate());
    }
  }

  public ResponseBuilder<Election> addProfileToElection(Election election, Profile profile) {
    election.getBallot().addCandidate(profile);

    return ResponseBuilder.ofValue(election);
  }

  public ResponseBuilder<Committee> fetchCommittee(String committeeName) {
    try {
      return ResponseBuilder.ofValue(committeeGateway.getCommittee(committeeName));
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ResponseBuilder.ofResponse(ResponseFactory.unknownCommitteeName());
    }
  }

  public <T> void save(T entity) {
    electionGateway.save();
  }

  public void addElection(Election election) {
    electionGateway.addElection(election);
  }

  public void submitRecord(VoteRecord voteRecord) {
    electionGateway.recordVote(voteRecord);
  }

  public List<VoteRecord> getAllVotes(Election election) {
    return electionGateway.getAllVotes(election);
  }

}