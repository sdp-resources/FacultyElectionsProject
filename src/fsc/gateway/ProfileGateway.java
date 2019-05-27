package fsc.gateway;

import fsc.entity.Profile;

import java.util.List;

public interface ProfileGateway {
  Profile getProfile(String username) throws InvalidProfileUsernameException;
  List<Profile> getAllProfiles();
  void addProfile(Profile profile);
  boolean hasProfile(String username);
  void save();
  class InvalidProfileUsernameException extends Exception {}
}
