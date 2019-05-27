package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class RemoveFromBallotInteractor {
  private ProfileGateway profileGateway;
  private ElectionGateway electionGateway;

  public RemoveFromBallotInteractor(
        ProfileGateway profileGateway, ElectionGateway electionGateway
  ) {
    this.profileGateway = profileGateway;

    this.electionGateway = electionGateway;
  }

  public Response execute(RemoveFromBallotRequest request) {

    try {
      Election election = electionGateway.getElection(request.getBallotID());
      Profile profile = profileGateway.getProfile(request.getProfileUsername());
      election.getBallot().remove(profile);
      electionGateway.save();
      return new SuccessResponse();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (Ballot.NoProfileInBallotException e) {
      return ErrorResponse.invalidCandidate();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

}
