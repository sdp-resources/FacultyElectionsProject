package fsc.interactor;

import fsc.entity.VoteRecord;
import fsc.gateway.ProfileGateway;
import fsc.gateway.VoteRecordGateway;
import fsc.response.ProfileDoesNotExistResponse;
import fsc.request.VoteRecordRequest;
import fsc.response.Response;
import fsc.response.AddedNewVoteResponse;


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
    voteGateway.recordVote(createVoteObject(request));
      return new AddedNewVoteResponse();
    }

  private VoteRecord createVoteObject(VoteRecordRequest request) throws ProfileGateway.InvalidProfileUsernameException {
    return new VoteRecord(profileGateway.getProfile(request.username), request.date, request.vote, request.electionID);
  }

}



