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
import fsc.utils.builder.Builder;

import java.util.function.Consumer;

public class ElectionInteractor extends Interactor {
  private BallotCreator ballotCreator;
  private ElectionFetcher electionFetcher;

  public ElectionInteractor(
        ElectionGateway electionGateway, CommitteeGateway committeeGateway,
        ProfileGateway profileGateway, EntityFactory entityFactory
  ) {
    this.ballotCreator = new BallotCreator(profileGateway, entityFactory);
    this.electionFetcher = new ElectionFetcher(electionGateway, profileGateway, committeeGateway,
                                               entityFactory);
  }

  public Response execute(CreateElectionRequest request) {
    return electionFetcher.fetchSeat(request.committeeName, request.seatName)
                          .mapThrough(this::createElection)
                          .perform(electionFetcher::addElection)
                          .perform(electionFetcher::save)
                          .mapThrough(Builder.lift(Election::getID))
                          .resolveWith(ResponseFactory::ofString);
  }

  public Response execute(EditBallotQueryRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .perform(setupBallotFromQuery(request.query))
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(ViewCandidatesRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .mapThrough(Builder.lift(Election::getCandidateProfiles))
                          .resolveWith(ResponseFactory::ofProfileList);
  }

  public Response execute(AddToBallotRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .bindWith(electionFetcher.fetchProfile(request.username),
                                    electionFetcher::addProfileToElection)
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(RemoveFromBallotRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .bindWith(electionFetcher.fetchProfile(request.username),
                                    electionFetcher::removeProfile)
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());

  }

  public Response execute(SubmitVoteRecordRequest request) {
    return electionFetcher.fetchVoterOnlyIfNoRecord(request.username, request.electionID)
                          .bindWith(electionFetcher.fetchProfilesIfNoDuplicates(request.vote),
                                    Builder.lift(electionFetcher::createVoteRecord))
                          .escapeIf(VoteRecord::someProfilesAreNotCandidates,
                                    ResponseFactory.invalidCandidate())
                          .perform(electionFetcher::submitRecord)
                          .perform(electionFetcher::save)
                          .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(ViewVoteRecordRequest request) {
    return electionFetcher.fetchVoter(request.username, request.electionID)
                          .mapThrough(electionFetcher::fetchRecord)
                          .resolveWith(ResponseFactory::ofVoteRecord);
  }

  public Response execute(ViewAllVotesRequest request) {
    return electionFetcher.fetchElection(request.electionID)
                          .mapThrough(Builder.lift(electionFetcher::getAllVotes))
                          .resolveWith(ResponseFactory::ofVoteRecordList);
  }

  private Builder<Election, Response> createElection(Seat seat) {
    Query defaultQuery = seat.getDefaultQuery();
    Ballot ballot = ballotCreator.getBallotFromQuery(defaultQuery);
    // TODO: Elections should no longer need a committee
    Election election = electionFetcher.createElection(seat.getCommittee(), seat, defaultQuery,
                                                       ballot);
    return Builder.ofValue(election);
  }

  private Consumer<Election> setupBallotFromQuery(Query query) {
    return election -> {
      election.setDefaultQuery(query);
      Ballot ballot = ballotCreator.getBallotFromQuery(election.getDefaultQuery());
      election.setBallot(ballot);
    };
  }

}
