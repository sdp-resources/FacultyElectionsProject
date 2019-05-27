package fsc.interactor;

import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.VoteRecordRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class VoteInteractor {

  private ProfileGateway profileGateway;
  private ElectionGateway voteGateway;

  public VoteInteractor(ElectionGateway voteRecordGateway, ProfileGateway profileGateway) {
    this.voteGateway = voteRecordGateway;
    this.profileGateway = profileGateway;
  }

  public Response execute(VoteRecordRequest request) {
    try {
      profileGateway.getProfile(request.username);
      voteGateway.recordVote(createVoteObject(request));
      return new SuccessResponse();
    } catch (Exception e) {
      return ErrorResponse.unknownProfileName();
    }
  }

  private VoteRecord createVoteObject(VoteRecordRequest request)
        throws ProfileGateway.InvalidProfileUsernameException {
    return new VoteRecord(profileGateway.getProfile(request.username), request.date, request.vote,
                          request.electionID);
  }

}



