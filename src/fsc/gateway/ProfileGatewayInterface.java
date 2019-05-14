package fsc.gateway;

import fsc.entity.Profile;

import java.util.ArrayList;

public interface ProfileGatewayInterface {
  public ArrayList<Profile> profileList = null;
  public ArrayList<Profile> getProfiles();
  public Profile getProfileWithUsername(String username);
  public Profile addProfile(Profile profile);
  public void clearProfileList();
  Profile getProfile(String userName);
}
