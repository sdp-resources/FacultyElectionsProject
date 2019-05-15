package fsc.gateway;

import fsc.entity.Profile;

import java.util.ArrayList;

public interface ProfileGatewayInterface {
  public ArrayList<Profile> profileList = null;
  public ArrayList<Profile> getProfiles();
  public Profile getProfileWithUsername(String username) throws Exception;
  public Profile addProfile(Profile profile);
  public void clearProfileList();
  public Boolean isValidDivision(String division);
  Profile getProfile(String userName);

}
