package fsc.interactor;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.VoteRecordRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CastVoteInteractor {

  private ProfileGateway profileGateway;
  private ElectionGateway electionGateway;

  public CastVoteInteractor(ElectionGateway electionGateway, ProfileGateway profileGateway) {
    this.electionGateway = electionGateway;
    this.profileGateway = profileGateway;
  }

  public Response execute(VoteRecordRequest request) {
    try {
      if (electionGateway.hasVoteRecord(request.username, request.electionID)) {
        return ErrorResponse.alreadyVoted();
      }
      profileGateway.getProfile(request.username);
      Election election = electionGateway.getElection(request.electionID);
      Profile voter = profileGateway.getProfile(request.username);
      List<Profile> votes = profileGateway.getProfiles(request.vote);
      for (Profile vote : votes) {
        if (!election.hasCandidate(vote.getUsername())) {
          return ErrorResponse.invalidCandidate();
        }
      }
      Date now = Calendar.getInstance().getTime();
      VoteRecord voteRecord = new VoteRecord(voter, now, votes, election);
      electionGateway.recordVote(voteRecord);
      electionGateway.save();
      return new SuccessResponse();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

}



