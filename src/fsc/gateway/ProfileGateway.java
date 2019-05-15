package fsc.gateway;

import fsc.entity.Profile;

import java.util.ArrayList;
import java.util.List;

public interface ProfileGateway {
  Profile getProfileFromUsername(String username);
  List<Profile> getAllProfiles();
  Profile addProfile(Profile profile);
}
