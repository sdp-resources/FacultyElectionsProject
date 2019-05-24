package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.AddToBallotRequest;
import fsc.response.*;

public class AddToBallotInteractor {
  private final BallotGateway ballotGateway;
  private final ProfileGateway profileGateway;

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
      profile = profileGateway.getProfile(request.getProfileUsername());
      ballot.add(profile);
      ballotGateway.save();
    } catch (BallotGateway.InvalidBallotIDException e) {
      return new ErrorResponse(ErrorResponse.NO_BALLOT_WITH_THAT_ID);
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return new ErrorResponse(ErrorResponse.NO_PROFILE_WITH_THAT_USERNAME);
    }
    return new SuccessfullyAddedProfileToBallotResponse();
  }
}
