package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.HashMap;

public class ProfileReviewInteractor {
  public String userName;
  public HashMap<String, String> userInfo = null;
  public ProfileGatewayInterface gateway;

  public ProfileReviewInteractor(ProfileGatewayInterface gateway){
    this.gateway = gateway;
  }

  public ProfileViewerResponse execute(ProfileViewerRequest request) throws noProfileException {
    userName = request.userName;
    try{
      Profile profile = gateway.getProfile(userName);
      ProfileViewer profileInfo = new ProfileViewer(profile);
      userInfo = profileInfo.createHashMap();
      ProfileViewerResponse response = new ProfileViewerResponse(userInfo);
      return response;
    }
    catch (Exception e){
      throw new noProfileException("No profile found!");
    }
  }

  private class noProfileException extends Throwable {
    public noProfileException(String e) {

    }
  }
}