package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.*;
import fsc.response.*;
import fsc.response.builder.PartialResponse;
import fsc.response.builder.UniPartialResponse;

import java.util.HashSet;
import java.util.List;

public class ElectionInteractor extends Interactor {
  private ElectionGateway electionGateway;
  private CommitteeGateway committeeGateway;
  private BallotCreator ballotCreator;
  private ProfileGateway profileGateway;

  public ElectionInteractor(
        ElectionGateway electionGateway, CommitteeGateway committeeGateway,
        ProfileGateway profileGateway
  ) {
    this.electionGateway = electionGateway;
    this.committeeGateway = committeeGateway;
    this.profileGateway = profileGateway;
    this.ballotCreator = new BallotCreator(profileGateway);
  }

  private static boolean thereAreMultipleProfileOccurences(List<Profile> profiles) {
    HashSet profilesSet = new HashSet<>(profiles);
    return profilesSet.size() != profiles.size();
  }

  public Response execute(CreateElectionRequest request) {
    try {
      Committee committee = committeeGateway.getCommittee(request.committeeName);
      Seat seat = committee.getSeat(request.seatName);
      Ballot ballot = ballotCreator.getBallot(seat.getDefaultQuery());
      Election election = new Election(seat, committee, seat.getDefaultQuery(), ballot);
      electionGateway.addElection(election);
      electionGateway.save();
      return ResponseFactory.ofString(election.getID());
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ResponseFactory.unknownCommitteeName();
    } catch (Committee.UnknownSeatNameException e) {
      return ResponseFactory.unknownSeatName();
    }
  }

  public Response execute(EditBallotQueryRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      election.setDefaultQuery(request.query);
      Ballot ballot = ballotCreator.getBallot(election.getDefaultQuery());
      election.setBallot(ballot);
      electionGateway.save();
      return ResponseFactory.success();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    }
  }

  public Response execute(ViewCandidatesRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      return ResponseFactory.ofProfileList(election.getCandidateProfiles());
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    }
  }

  public Response execute(AddToBallotRequest request) {
    try {
      Election election = electionGateway.getElection(request.getElectionId());
      Profile profile = profileGateway.getProfile(request.getProfileUsername());
      election.getBallot().addCandidate(profile);
      electionGateway.save();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseFactory.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    }
    return ResponseFactory.success();
  }

  public Response execute(RemoveFromBallotRequest request) {
    try {
      Election election = electionGateway.getElection(request.ballotID);
      Profile profile = profileGateway.getProfile(request.username);
      election.getBallot().remove(profile);
      electionGateway.save();
      return ResponseFactory.success();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseFactory.unknownProfileName();
    } catch (Ballot.NoProfileInBallotException e) {
      return ResponseFactory.invalidCandidate();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    }
  }

  public Response execute(SubmitVoteRecordRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      Profile voter = profileGateway.getProfile(request.username);
      if (electionGateway.hasVoteRecord(voter, election)) {
        return ResponseFactory.alreadyVoted();
      }
      List<Profile> votes = profileGateway.getProfiles(request.vote);

      if (thereAreMultipleProfileOccurences(votes)) {
        return ResponseFactory.multipleRanksForCandidate();
      }

      if (someProfilesAreNotCandidates(election, votes)) {
        return ResponseFactory.invalidCandidate();
      }

      electionGateway.recordVote(new VoteRecord(voter, votes, election));
      electionGateway.save();
      return ResponseFactory.success();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseFactory.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    }
  }

  public Response execute(ViewVoteRecordRequest request) {
    try {
      Profile voter = profileGateway.getProfile(request.username);
      Election election = electionGateway.getElection(request.electionID);
      VoteRecord voteRecord = electionGateway.getVoteRecord(voter, election);
      return ResponseFactory.ofVoteRecord(voteRecord);
    } catch (ElectionGateway.NoVoteRecordException e) {
      return ResponseFactory.noVote();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseFactory.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    }
  }

  public Response execute(ViewAllVotesRequest request) {
    return getElection(request.electionID)
                 .map(this::getAllVotes)
                 .resolveWith(ResponseFactory::ofVoteRecordList);
  }

  public List<VoteRecord> getAllVotes(Election election) {
    return electionGateway.getAllVotes(election);
  }

  public UniPartialResponse<Election> getElection(String electionID) {
    try {
      Election election = electionGateway.getElection(electionID);
      return PartialResponse.supply(election);
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return PartialResponse.resolve(ResponseFactory.unknownElectionID());
    }
  }

  private boolean someProfilesAreNotCandidates(Election election, List<Profile> votes) {
    for (Profile vote : votes) {
      if (!election.hasCandidate(vote)) return true;
    }
    return false;
  }
}
