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
import java.util.function.Function;

public class ElectionInteractor extends Interactor {
  private BallotCreator ballotCreator;
  private ElectionFetcher electionFetcher;

  public ElectionInteractor(
        ElectionGateway electionGateway, CommitteeGateway committeeGateway,
        ProfileGateway profileGateway, EntityFactory entityFactory
  ) {
    this.ballotCreator = new BallotCreator(profileGateway);
    this.electionFetcher = new ElectionFetcher(electionGateway, profileGateway, committeeGateway,
                                               entityFactory);
  }

  public Response execute(CreateElectionRequest request) {
    return electionFetcher.fetchCommittee(request.committeeName)
                          .mapThrough(createElection(request.seatName))
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

  private Function<Committee, Builder<Election, Response>> createElection(String seatName) {
    return committee -> {
      try {
        Seat seat;
        seat = committee.getSeat(seatName);
        Query defaultQuery = seat.getDefaultQuery();
        Ballot ballot = ballotCreator.getBallot(defaultQuery);
        Election election = electionFetcher.createElection(committee, seat, defaultQuery, ballot);
        return Builder.ofValue(election);
      } catch (Committee.UnknownSeatNameException e) {
        return Builder.ofResponse(ResponseFactory.unknownCommitteeName());
      }
    };
  }

  private Consumer<Election> setupBallotFromQuery(Query query) {
    return election -> {
      election.setDefaultQuery(query);
      Ballot ballot = ballotCreator.getBallot(election.getDefaultQuery());
      election.setBallot(ballot);
    };
  }

}
