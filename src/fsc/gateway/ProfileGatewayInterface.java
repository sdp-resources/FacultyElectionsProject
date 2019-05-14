package fsc.gateway;

import fsc.entity.Profile;

import java.util.ArrayList;

public interface ProfileGatewayInterface {
  public ArrayList<Profile> profileList = null;
  public ArrayList<Profile> getProfiles();
  public Profile getProfileWitheUsername(String username, ArrayList<Profile> profiles);
  public Profile addProfile(Profile profile);
  public void clearProfileList();
}
