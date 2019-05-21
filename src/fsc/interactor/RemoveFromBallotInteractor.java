package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyRemovedProfileFromBallotResponse;

public class RemoveFromBallotInteractor {
  private BallotGateway ballotGateway;
  private ProfileGateway profileGateway;

  public RemoveFromBallotInteractor(
        BallotGateway ballotGateway, ProfileGateway profileGateway) {
    this.ballotGateway = ballotGateway;
    this.profileGateway = profileGateway;

  }

  public Response execute(RemoveFromBallotRequest request) {
    Ballot ballot;
    Profile profile;

    try {
      ballot = ballotGateway.getBallot(request.getBallotID());
      profile = profileGateway.getProfile(request.getProfileUsername());
      ballot.remove(profile);
      ballotGateway.save();
      return new SuccessfullyRemovedProfileFromBallotResponse();
    } catch (BallotGateway.InvalidBallotIDException e) {
      return new ErrorResponse("No ballot with that ID");
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return new ErrorResponse("No profile with that username");
    } catch (Ballot.NoProfileInBallotException e) {
      return new ErrorResponse("Ballot does not contain profile");
    }
  }
}
