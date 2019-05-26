package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.*;

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
      Profile candidate = profileGateway.getProfile(request.getProfileUsername());

      ballot.remove(candidate);
      ballotGateway.save();
      return new SuccessResponse();
    } catch (BallotGateway.InvalidBallotIDException e) {
      return ErrorResponse.unknownBallotID();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (Ballot.NoProfileInBallotException e) {
      return ErrorResponse.invalidCandidate();
    }
  }

}
