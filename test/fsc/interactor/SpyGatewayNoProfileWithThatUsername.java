package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class SpyGatewayNoProfileWithThatUsername implements ProfileGatewayInterface {
  public static ArrayList<Profile> profileList;
  public static String submittedUsername;

  public SpyGatewayNoProfileWithThatUsername(){
    this.profileList = null;
  }


  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileWitheUsername(String username, ArrayList<Profile> profiles) {
    submittedUsername = username;
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {

  }

}

