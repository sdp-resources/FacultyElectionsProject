package fsc.interactor;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.AddToBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class AddToBallotInteractor {
  private ProfileGateway profileGateway;
  private ElectionGateway electionGateway;

  public AddToBallotInteractor(
        ProfileGateway profileGateway, ElectionGateway electionGateway
  ) {
    this.profileGateway = profileGateway;
    this.electionGateway = electionGateway;
  }

  public Response execute(AddToBallotRequest request) {
    try {
      Election election = electionGateway.getElection(request.getBallotID());
      Profile profile = profileGateway.getProfile(request.getProfileUsername());
      election.getBallot().add(profile);
      electionGateway.save();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
    return new SuccessResponse();
  }

}
