package fsc.interactor.fetcher;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.List;

public class ElectionFetcher extends CommitteeFetcher {
  private ElectionGateway electionGateway;
  private CommitteeGateway committeeGateway;
  private EntityFactory entityFactory;

  public ElectionFetcher(
        ElectionGateway electionGateway,
        ProfileGateway profileGateway,
        CommitteeGateway committeeGateway, EntityFactory entityFactory
  ) {
    super(committeeGateway,  profileGateway, entityFactory);
    this.electionGateway = electionGateway;
    this.committeeGateway = committeeGateway;
    this.entityFactory = entityFactory;
  }

  public Builder<VoteRecord, Response> fetchRecord(Voter voter) {
    try {
      return Builder.ofValue(electionGateway.getVoteRecord(voter));
    } catch (ElectionGateway.NoVoteRecordException e) {
      return Builder.ofResponse(ResponseFactory.noVote());
    }
  }

  public Builder<Election, Response> fetchElection(String electionID) {
    try {
      return Builder.ofValue(electionGateway.getElection(electionID));
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return Builder.ofResponse(ResponseFactory.unknownElectionID());
    }
  }

  public Builder<Voter, Response> fetchVoter(String username, String electionId) {
    return fetchProfile(username)
                 .bindWith(fetchElection(electionId),
                           Builder.lift(entityFactory::createVoter));

  }

  public Builder<Voter, Response> fetchVoterOnlyIfNoRecord(String username, String electionID) {
    return fetchVoter(username, electionID)
                 .escapeIf(electionGateway::hasVoteRecord,
                           ResponseFactory.alreadyVoted());
  }

  public Builder<Election, Response> removeProfile(Election election, Profile profile) {
    try {
      election.getBallot().remove(profile);
      return Builder.ofValue(election);
    } catch (Ballot.NoProfileInBallotException e) {
      return Builder.ofResponse(ResponseFactory.invalidCandidate());
    }
  }

  public Builder<Election, Response> addProfileToElection(Election election, Profile profile) {
    election.getBallot()
            .add(entityFactory.createCandidate(profile, election.getBallot()));

    return Builder.ofValue(election);
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

  public VoteRecord createVoteRecord(Voter voter, List<Profile> votes) {
    return entityFactory.createVoteRecord(voter, votes);
  }

  public Election createElection(
        Seat seat, Query defaultQuery, Ballot ballot
  ) {
    return entityFactory.createElection(seat, defaultQuery, ballot);
  }
}