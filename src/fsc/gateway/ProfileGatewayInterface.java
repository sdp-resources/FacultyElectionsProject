package fsc.gateway;

import fsc.entity.Profile;

import java.util.ArrayList;

public interface ProfileGatewayInterface {
  public ArrayList<Profile> getProfiles();
  public static Profile getProfileWitheUsername(String username, ArrayList<Profile> profiles){
    return profiles.get(0);
  }
}
