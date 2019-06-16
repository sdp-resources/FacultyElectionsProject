package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.AddToBallotRequest;
import fsc.request.RemoveFromBallotRequest;
import fsc.request.VoteRecordRequest;
import fsc.response.*;

import java.util.HashSet;
import java.util.List;

public class BallotInteractor {
  private ProfileGateway profileGateway;
  private ElectionGateway electionGateway;

  public BallotInteractor(
        ElectionGateway electionGateway, ProfileGateway profileGateway
  ) {
    this.profileGateway = profileGateway;
    this.electionGateway = electionGateway;
  }

  public Response execute(AddToBallotRequest request) {
    try {
      Election election = electionGateway.getElection(request.getBallotID());
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

  public Response execute(VoteRecordRequest request) {
    try {
      if (electionGateway.hasVoteRecord(request.username, request.electionID)) {
        return ResponseFactory.alreadyVoted();
      }
      profileGateway.getProfile(request.username);
      Election election = electionGateway.getElection(request.electionID);
      Profile voter = profileGateway.getProfile(request.username);
      List<Profile> votes = profileGateway.getProfiles(request.vote);

      if (thereAreMultipleProfileOccurences(votes)) {
        return ResponseFactory.multipleRanksForCandidate();
      }

      if (someProfilesAreNotCandidates(election, votes)) {
        return ResponseFactory.invalidCandidate();
      }

      VoteRecord voteRecord = new VoteRecord(voter, votes, election);
      electionGateway.recordVote(voteRecord);
      electionGateway.save();
      return ResponseFactory.success();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseFactory.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    }
  }

  private boolean someProfilesAreNotCandidates(Election election, List<Profile> votes) {
    for (Profile vote : votes) {
      if (!election.hasCandidate(vote)) return true;
    }
    return false;
  }

  private static boolean thereAreMultipleProfileOccurences(List<Profile> profiles) {
    HashSet profilesSet = new HashSet<>(profiles);
    return profilesSet.size() != profiles.size();
  }

}
