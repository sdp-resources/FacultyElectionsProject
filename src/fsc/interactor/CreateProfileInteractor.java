package fsc.interactor;

import fsc.Main;
import fsc.gateway.ProfileGatewayInterface;
import fsc.request.CreateProfileRequest;
import fsc.entity.Profile;
import gateway.InMemoryGateway;
import spark.Response;

import java.util.ArrayList;

public class CreateProfileInteractor {

  private static ProfileGatewayInterface gateway;


  public CreateProfileInteractor(ProfileGatewayInterface gateway) {
    this.gateway = gateway;
  }

  public ArrayList<Profile> execute(
        CreateProfileRequest request) throws Exception {
    if (gateway.getProfileWitheUsername(request.username, gateway.profileList) != null) {
      throw new Exception("Username Already Used!");
    }
    Profile profile = new Profile(request.name, request.username, request.department,
                                  request.contract);
    gateway.addProfile(profile);
    //fsc.response.Response goodResponse = new fsc.response.Response();
    return gateway.profileList;
  }

}
