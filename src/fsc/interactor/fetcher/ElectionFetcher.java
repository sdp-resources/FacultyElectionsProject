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
  private EntityFactory entityFactory;

  public ElectionFetcher(
        ElectionGateway electionGateway,
        ProfileGateway profileGateway,
        CommitteeGateway committeeGateway, EntityFactory entityFactory
  ) {
    super(committeeGateway, profileGateway, entityFactory);
    this.electionGateway = electionGateway;
    this.entityFactory = entityFactory;
  }

  public Builder<VoteRecord, Response> fetchRecord(long recordId) {
    try {
      return Builder.ofValue(electionGateway.getVoteRecord(recordId));
    } catch (ElectionGateway.NoVoteRecordException e) {
      return Builder.ofResponse(ResponseFactory.noVote());
    }
  }

  public Builder<Election, Response> fetchElection(long electionID) {
    try {
      return Builder.ofValue(electionGateway.getElection(electionID));
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return Builder.ofResponse(ResponseFactory.unknownElectionID());
    }
  }

  public Builder<Election, Response> fetchElectionInSetupState(Long electionId) {
    return fetchElection(electionId)
                 .escapeUnless(Election::isInSetupState,
                               ResponseFactory.improperElectionState());
  }

  public Builder<Voter, Response> fetchVoter(long voterId) {
    try {
      return Builder.ofValue(electionGateway.getVoter(voterId));
    } catch (ElectionGateway.InvalidVoterException e) {
      return Builder.ofResponse(ResponseFactory.invalidVoter());
    }
  }

  public Builder<Voter, Response> fetchVoterOnlyIfNoRecord(long voterId) {
    return fetchVoter(voterId).escapeIf(Voter::hasVoted,
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

  public void submitRecord(VoteRecordPair pair) {
    pair.voter.setVoted(true);
    electionGateway.addVoteRecord(pair.voteRecord);
  }

  public List<VoteRecord> getAllVotes(Election election) {
    return electionGateway.getAllVotes(election);
  }

  public VoteRecordPair createVoteRecordPair(Voter voter, List<Profile> votes) {
    return new VoteRecordPair(voter,
                              entityFactory.createVoteRecord(voter.getElection(), votes));
  }

  public Election createElection(Seat seat, Query defaultQuery, Ballot ballot) {
    return entityFactory.createElection(seat, defaultQuery, ballot);
  }

  public Builder<Election.State, Response> getStateFromString(String state) {
    try {
      return Builder.ofValue(Election.State.valueOf(state));
    } catch (java.lang.IllegalArgumentException e) {
      return Builder.ofResponse(ResponseFactory.invalidElectionState());
    }
  }

  public Builder<Voter, Response> createVoter(Profile profile, Election election) {
    return Builder.ofValue(entityFactory.createVoter(profile, election));
  }

  public Builder<Voter, Response> addVoter(Voter voter) {
    try {
      electionGateway.addVoter(voter);
      return Builder.ofValue(voter);
    } catch (ElectionGateway.ExistingVoterException e) {
      return Builder.ofResponse(ResponseFactory.voterExists());
    }
  }

  public class VoteRecordPair {
    public final Voter voter;
    public final VoteRecord voteRecord;

    VoteRecordPair(Voter voter, VoteRecord voteRecord) {
      this.voter = voter;
      this.voteRecord = voteRecord;
    }
  }
}