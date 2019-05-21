package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ProfileGateway;
import fsc.gateway.VoteRecordGateway;
import fsc.response.ProfileDoesNotExistResponse;
import fsc.request.VoteRecordRequest;
import fsc.response.Response;
import fsc.response.AddedNewVoteResponse;

import java.util.Date;

public class VoteInteractor {

  private final ProfileGateway profileGateway;
  VoteRecordGateway voteGateway;

  public VoteInteractor(VoteRecordGateway voteRecordGateway, ProfileGateway profileGateway) {
    this.voteGateway = voteRecordGateway;
    this.profileGateway = profileGateway;
  }

  public Response execute(VoteRecordRequest request) throws Exception {
    try {profileGateway.getProfile(request.username);}
    catch (Exception e) {
      return new ProfileDoesNotExistResponse();
    }
      VoteRecord voteRecord = createVoteObject(request);
      voteGateway.recordVote(voteRecord);
      return new AddedNewVoteResponse();
    }

  private VoteRecord createVoteObject(VoteRecordRequest request) throws ProfileGateway.InvalidProfileUsernameException {
    String username = request.username;
    Date date = request.date;
    String vote = request.vote;
    int electionID = request.electionID;
    Profile profile = profileGateway.getProfile(username);

    return new VoteRecord(profile,vote,electionID);
  }

}



