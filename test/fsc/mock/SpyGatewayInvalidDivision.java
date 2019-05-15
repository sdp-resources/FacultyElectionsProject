package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class SpyGatewayInvalidDivision implements ProfileGatewayInterface {
  public ArrayList<Profile> profileList;
  public String submittedUsername;
  public String submittedDivision;

  public SpyGatewayInvalidDivision(){
    this.profileList = null;
  }


  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileWithUsername(String username) throws Exception {
    submittedUsername = username;
    throw new Exception("No Profile with that name Here");
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {}

  public Boolean isValidDivision(String division) {
    submittedDivision = division;
    return false;
  }

  public Profile getProfile(String userName) {
    return null;
  }

}