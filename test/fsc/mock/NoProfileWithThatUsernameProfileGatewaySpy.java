package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class NoProfileWithThatUsernameProfileGatewaySpy implements ProfileGatewayInterface {
  public ArrayList<Profile> profileList;
  public String submittedUsername;

  public NoProfileWithThatUsernameProfileGatewaySpy(){
    this.profileList = null;
  }



  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileWithUsername(String username) {
    submittedUsername = username;
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {

  }

  public Profile getProfile(String userName) {
    return null;
  }

}

