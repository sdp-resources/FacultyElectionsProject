package fsc.gateway;

import fsc.entity.Profile;

import java.util.List;

public interface ProfileGateway {
  Profile getProfile(String username) throws InvalidProfileUsernameException;
  List<Profile> getAllProfiles();
  default List<Profile> getActiveProfiles() { return getAllProfiles(); }
  void addProfile(Profile profile);
  boolean hasProfile(String username);
  void save();
  class InvalidProfileUsernameException extends Exception {}
}
