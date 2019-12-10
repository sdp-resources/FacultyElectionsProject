package fsc.gateway;

import fsc.entity.Profile;
import fsc.entity.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ProfileGateway {
  Profile getProfile(String username) throws InvalidProfileUsernameException;
  List<Profile> getAllProfiles();
  default List<Profile> getProfiles(List<String> usernames) throws InvalidProfileUsernameException {
    List<Profile> list = new ArrayList<>();
    for (String username : usernames) list.add(getProfile(username));
    return list;
  }
  default List<Profile> getProfilesMatching(Query query) {
    return getAllProfiles().stream()
                           .filter(query::isProfileValid)
                           .collect(Collectors.toList());
  }
  void addProfile(Profile profile);
  boolean hasProfile(String username);
  void save();
  class InvalidProfileUsernameException extends Exception {}
}
