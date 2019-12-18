package fsc.interactor.fetcher;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;
import fsc.voting.ElectionRecord;
import fsc.voting.Vote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

  public ElectionBuilder fetchElection(long electionID) {
    try {
      return new ElectionBuilder(Builder.ofValue(electionGateway.getElection(electionID)));
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return new ElectionBuilder(Builder.ofResponse(ResponseFactory.unknownElectionID()));
    }
  }

  public ElectionBuilder fetchElectionInValidChangeVoterState(Long electionId) {
    return fetchElection(electionId).reportImproperStateUnless(State::canChangeVoters);
  }

  public VoterBuilder fetchVoter(long voterId) {
    try {
      return new VoterBuilder(Builder.ofValue(electionGateway.getVoter(voterId)));
    } catch (ElectionGateway.InvalidVoterException e) {
      return new VoterBuilder(Builder.ofResponse(ResponseFactory.invalidVoter()));
    }
  }

  public VoterBuilder fetchVoterOnlyIfNoRecord(long voterId) {
    return fetchVoter(voterId).escapeIfVoted();
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

  public Builder<State, Response> getStateFromString(String state) {
    try {
      return Builder.ofValue(State.valueOf(state));
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

  public Builder<Collection<Election>, Response> fetchActiveElections() {
    Collection<Election> activeElections =
          electionGateway.getAllElections()
                         .stream().filter(e -> e.getState().isActive())
                         .collect(Collectors.toList());
    return Builder.ofValue(activeElections);
  }

  public Builder<Voter, Response> locateVoter(Election election, Profile profile) {
    Voter voter = election.getVoter(profile);

    return voter == null ?
           Builder.ofResponse(ResponseFactory.invalidVoter()) :
           Builder.ofValue(voter);
  }

  public Builder<Voter, Response> removeVoter(Profile profile, Election election) {
    Voter voter = election.getVoter(profile);
    if (voter == null) {
      return Builder.ofResponse(ResponseFactory.voterMissing());
    }
    election.removeVoter(voter);
    return Builder.ofValue(voter);
  }

  public Builder<ElectionRecord, Response> fetchElectionResults(long electionID) {
    return fetchElection(electionID)
                 .mapThrough(this::retrieveRecord);
  }

  private Builder<ElectionRecord, Response> retrieveRecord(Election e) {
    List<Vote> votes = e.getVoteRecords().stream()
                        .map(VoteRecord::getVotes)
                        .map(Vote::fromUsernames)
                        .collect(Collectors.toList());

    ElectionRecord electionRecord = new ElectionRecord(votes);
    electionRecord.runElection();
    return Builder.ofValue(electionRecord);
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