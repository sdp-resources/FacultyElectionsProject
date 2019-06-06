package fsc.gateway;

import fsc.entity.Profile;
import fsc.entity.query.Query;

import java.util.ArrayList;
import java.util.List;

public interface ProfileGateway {
  Profile getProfile(String username) throws InvalidProfileUsernameException;
  default List<Profile> getProfiles(List<String> usernames) throws InvalidProfileUsernameException {
    List<Profile> list = new ArrayList<>();
    for (String username : usernames) list.add(getProfile(username));
    return list;
  }
  List<Profile> getAllProfiles();
  List<Profile> getProfilesMatching(Query query);
  void addProfile(Profile profile);
  boolean hasProfile(String username);
  void save();
  class InvalidProfileUsernameException extends Exception {}
}
