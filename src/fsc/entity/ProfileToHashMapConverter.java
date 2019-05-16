package fsc.entity;

import java.util.*;

public class ProfileToHashMapConverter {

  public ProfileToHashMapConverter() {

  }

  public Map<String, String> createHashMap(Profile profile) {
    Map<String, String> profileView = new HashMap<String, String>();
    profileView.put("Username", profile.getUsername());
    profileView.put("Name", profile.getName());
    profileView.put("Department", profile.getDivision());
    profileView.put("Contract", profile.getContract());
    return profileView;
  }
}