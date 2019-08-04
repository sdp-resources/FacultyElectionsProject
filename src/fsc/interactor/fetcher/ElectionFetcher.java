package fsc.interactor.fetcher;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    election.removeCandidate(profile);
    return Builder.ofValue(election);

  }

  public Builder<Election, Response> addProfileToElection(Election election, Profile profile) {
    election.getCandidates()
            .add(entityFactory.createCandidate(profile, election));

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

  public Collection<VoteRecord> getAllVotes(Election election) {
    return electionGateway.getAllVotes(election);
  }

  public VoteRecordPair createVoteRecordPair(Voter voter, List<Profile> votes) {
    return new VoteRecordPair(voter,
                              entityFactory.createVoteRecord(voter.getElection(), votes));
  }

  public Election createElection(Seat seat) {
    return entityFactory.createElection(seat);
  }

  public Builder<Election.State, Response> getStateFromString(String state) {
    try {
      return Builder.ofValue(Election.State.valueOf(state));
    } catch (java.lang.IllegalArgumentException e) {
      return Builder.ofResponse(ResponseFactory.invalidElectionState());
    }
  }

  public Builder<Voter, Response> createVoter(Profile profile, Election election) {
    return Builder.ofValue(makeVoter(profile, election));
  }

  public Voter makeVoter(Profile profile, Election election) {
    return entityFactory.createVoter(profile, election);
  }

  public Builder<Voter, Response> addVoter(Voter voter) {
    try {
      electionGateway.addVoter(voter);
      return Builder.ofValue(voter);
    } catch (ElectionGateway.ExistingVoterException e) {
      return Builder.ofResponse(ResponseFactory.voterExists());
    }
  }

  public void repopulateCandidatesListFromStoredQuery(Election election) {
    election.setCandidates(new ArrayList<>());
    addValidCandidatesToElection(election.getCandidateQuery(), election);
  }

  public void addValidCandidatesToElection(Query query, Election election) {
    for (Profile profile : profileGateway.getProfilesMatching(query)) {
      election.addCandidate(entityFactory.createCandidate(profile, election));
    }
  }

  public Builder<Collection<Election>, Response> fetchAllElections() {
    return Builder.ofValue(electionGateway.getAllElections());
  }

  public Function<Election, Builder<Candidate, Response>> getCandidate(String username) {
    return election -> {
      try {
        return Builder.ofValue(election.getCandidateByUsername(username));
      } catch (ElectionGateway.NoProfileInBallotException e) {
        return Builder.ofResponse(ResponseFactory.invalidCandidate());
      }
    };
  }

  public Builder<Collection<Election>, Response> fetchActiveElections() {
    Collection<Election> activeElections =
          electionGateway.getAllElections()
                         .stream().filter(Election::isActive)
                         .collect(Collectors.toList());
    return Builder.ofValue(activeElections);
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