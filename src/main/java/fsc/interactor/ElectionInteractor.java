package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.interactor.fetcher.ElectionFetcher;
import fsc.request.*;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.MyUtils;
import fsc.utils.builder.Builder;

import java.util.List;
import java.util.function.Function;

public class ElectionInteractor extends Interactor {
  private ElectionFetcher electionFetcher;

  public ElectionInteractor(
        ElectionGateway electionGateway, CommitteeGateway committeeGateway,
        ProfileGateway profileGateway, EntityFactory entityFactory
  ) {
    this.electionFetcher = new ElectionFetcher(electionGateway, profileGateway, committeeGateway,
                                               entityFactory);
  }

  public Response execute(CreateElectionRequest request) {
    return electionFetcher.fetchSeat(request.seatId)
                          .mapThrough(this::createElection)
                          .perform(electionFetcher::addElection)
                          .perform(electionFetcher::save)
                          .resolveWith(ResponseFactory::ofElection);
  }

  public Response execute(ViewAllElectionsRequest request) {
    return electionFetcher.fetchAllElections()
                          .resolveWith(ResponseFactory::ofElectionList);
  }

  public Response execute(ViewActiveElectionsRequest request) {
    return electionFetcher.fetchActiveElections()
                          .resolveWith(ResponseFactory::ofElectionList);
  }

  public Response execute(ViewVoterRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .reportImproperStateUnless(Election::isInVoteState)
                          .bindWith(electionFetcher.fetchProfile(request.username),
                                    electionFetcher::locateVoter)
                          .resolveWith(ResponseFactory::ofVoter);
  }

  public Response execute(EditBallotQueryRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .reportImproperStateUnless(Election::isInSetupState)
                          .perform(election -> setupNewDefaultQuery(election, request.query))
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(ViewCandidatesRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .getCandidateProfiles()
                          .resolveWith(ResponseFactory::ofProfileList);
  }

  public Response execute(AddToBallotRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .reportImproperStateUnless(Election::isInSetupState)
                          .bindWith(electionFetcher.fetchProfile(request.username),
                                    electionFetcher::addProfileToElection)
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(RemoveFromBallotRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .reportImproperStateUnless(Election::isInSetupState)
                          .bindWith(electionFetcher.fetchProfile(request.username),
                                    electionFetcher::removeProfile)
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());

  }

  public Response execute(SubmitVoteRecordRequest request) {
    return electionFetcher.fetchVoterOnlyIfNoRecord(request.voterId)
                          .reportImproperStateUnless(Voter::canVote)
                          .bindWith(electionFetcher.fetchProfilesIfNoDuplicates(request.vote),
                                    Builder.lift(electionFetcher::createVoteRecordPair))
                          .escapeIf(p -> p.voteRecord.someProfilesAreNotCandidates(),
                                    ResponseFactory.invalidCandidate())
                          .perform(electionFetcher::submitRecord)
                          .perform(electionFetcher::save)
                          .mapThrough(p -> Builder.ofValue(p.voteRecord))
                          .resolveWith(ResponseFactory::ofVoteRecord);
  }

  public Response execute(GetElectionResultsRequest request) {
    return electionFetcher.fetchElectionResults(request.electionID)
          .resolveWith(ResponseFactory::ofElectionRecord);
  }

  public Response execute(ViewVoteRecordRequest request) {
    return electionFetcher.fetchRecord(request.recordId)
                          .resolveWith(ResponseFactory::ofVoteRecord);
  }

  public Response execute(ViewAllVotesRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .mapThrough(Builder.lift(electionFetcher::getAllVotes))
                          .resolveWith(ResponseFactory::ofVoteRecordList);
  }

  public Response execute(EditElectionStateRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .bindWith(electionFetcher.getStateFromString(request.state),
                                    this::setElectionState)
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(ViewElectionRequest request) {
    return ((Builder<Election, Response>) electionFetcher.fetchElection(request.electionID))
                 .resolveWith(ResponseFactory::ofElection);
  }

  public Response execute(AddVoterRequest request) {
    return electionFetcher.fetchProfile(request.username)
                          .bindWith(electionFetcher.fetchElectionInSetupState(request.electionId),
                                    electionFetcher::createVoter)
                          .mapThrough(electionFetcher::addVoter)
                          .perform(electionFetcher::save)
                          .resolveWith(ResponseFactory::ofVoter);
  }

  public Response execute(RemoveVoterRequest request) {
    return electionFetcher.fetchProfile(request.username)
                          .bindWith(electionFetcher.fetchElectionInSetupState(request.electionId),
                                    electionFetcher::removeVoter)
                          .perform(electionFetcher::save)
                          .resolveWith(ResponseFactory::ofVoter);
  }

  public Response execute(ViewDTSRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .reportImproperStateIf(Election::isInSetupState)
                          .retrieveCandidate(request.username)
                          .resolveWith(ResponseFactory::ofCandidate);
  }

  public Response execute(SetDTSRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .reportImproperStateUnless(Election::isInDecideToStandState)
                          .retrieveCandidate(request.username)
                          .mapThrough(setStatusFromString(request.status))
                          .perform(electionFetcher::save)
                          .resolveWith(c -> ResponseFactory.success());
  }

  private Function<Candidate, Builder<Candidate, Response>> setStatusFromString(String status) {
    return (Candidate candidate) -> {
      try {
        candidate.setStatus(Candidate.Status.valueOf(status));
        return Builder.ofValue(candidate);
      } catch (IllegalArgumentException e) {
        return Builder.ofResponse(ResponseFactory.invalidCandidateStatus());
      }
    };
  }

  private Builder<Election, Response> setElectionState(Election election, Election.State state) {
    election.setState(state);
    if (election.isInVoteState()) {
      automaticallyAddUndecidedCandidates(election);
    }
    return Builder.ofValue(election);
  }

  private void automaticallyAddUndecidedCandidates(Election election) {
    // TODO: Allow listeners to react
    for (Candidate candidate : election.getCandidates()) {
      if (candidate.hasNotResponded()) { candidate.setStatusAccepted(); }
    }

  }

  private Builder<Election, Response> createElection(Seat seat) {
    Election election = electionFetcher.createElection(seat);
    setupNewDefaultQuery(election, seat.getCandidateQuery());
    return populateVoterListForElection(election);
  }

  private Builder<Election, Response> populateVoterListForElection(Election election) {
    Query voterQuery = election.getSeat().getCommittee().getVoterQuery();
    List<Profile> voterProfiles = electionFetcher.getProfilesMatchingQuery(voterQuery);
    election.setVoters(MyUtils.convertList(voterProfiles,
                                           p -> electionFetcher.makeVoter(p, election)));
    return Builder.ofValue(election);
  }

  private void setupNewDefaultQuery(Election election, Query query) {
    election.setCandidateQuery(query);
    electionFetcher.repopulateCandidatesListFromStoredQuery(election);
  }

}
