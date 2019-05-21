package fsc.gateway;

import fsc.entity.Profile;

import java.util.ArrayList;
import java.util.List;

public interface ProfileGateway {
  Profile getProfile(String username) throws InvalidProfileUsernameException;
  List<Profile> getAllProfiles();
  void addProfile(Profile profile);
  void saveProfile(Profile profile);
  class InvalidProfileUsernameException extends Exception {}
}
