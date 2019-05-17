package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.BallotCreator;
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

    try
    {
      ballot = ballotGateway.getBallot(request.getBallotID());
    }
    catch (BallotGateway.InvalidBallotIDException e)
    {
      return new ErrorResponse("No ballot with that ID");
    }

    try
    {
      profile = profileGateway.getProfileFromUsername(request.getProfileUsername());
    }
    catch (ProfileGateway.InvalidProfileUsernameException e)
    {
      return new ErrorResponse("No profile with that username");
    }

    try
    {
      ballotGateway.saveBallot(ballot);
    }
    catch (BallotGateway.CannotSaveBallotException e)
    {
      return new ErrorResponse("Ballot save failed");
    }


    try
    {
      ballot.remove(profile);
      return new SuccessfullyRemovedProfileFromBallotResponse();
    }
    catch (Ballot.NoProfileInBallotException e)
    {
      return new ErrorResponse("Ballot does not contain profile");
    }

  }

}
