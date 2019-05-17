package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.AddToBallotRequest;
import fsc.response.*;

public class AddToBallotInteractor {
  private BallotGateway ballotGateway;
  private ProfileGateway profileGateway;

  public AddToBallotInteractor(BallotGateway ballotGateway, ProfileGateway profileGateway)
  {
    this.ballotGateway = ballotGateway;
    this.profileGateway = profileGateway;
  }

  public Response execute(AddToBallotRequest request) {
    Ballot ballot;
    Profile profile;

    try {
      ballot = ballotGateway.getBallot(request.getBallotID());
      profile = profileGateway.getProfileFromUsername(request.getProfileUsername());
      ballot.add(profile);
      ballotGateway.saveBallot(ballot);
    } catch (BallotGateway.InvalidBallotIDException e) {
      return new ErrorResponse("No ballot with that ID");
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return new ErrorResponse("No profile with that username");
    } catch (BallotGateway.CannotSaveBallotException e) {
      return new ErrorResponse("Ballot save failed");
    }
    return new SuccessfullyAddedProfileToBallotResponse();
  }
}
