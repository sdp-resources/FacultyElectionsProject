package fsc.interactor;

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

  public Response executeGoodID(VoteRecordRequest request) throws Exception {
    try {profileGateway.getProfileFromUsername(request.username);} catch (Exception e) {
      return new ProfileDoesNotExistResponse();
    }
      return new AddedNewVoteResponse();
    }

  public Response executeBadID(VoteRecordRequest request) throws Exception {
    try {profileGateway.getProfileFromUsername(request.username);} catch (Exception e) {
      return new ProfileDoesNotExistResponse();
    }
      return  new AddedNewVoteResponse();
    }
  }



