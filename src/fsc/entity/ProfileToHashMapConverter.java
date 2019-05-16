package fsc.entity;

import fsc.entity.Profile;

import java.util.*;

public class ProfileToHashMapConverter {
  public Map<String, String> profileView = new HashMap<String, String>();

  public ProfileToHashMapConverter(Profile profile) {
    profileView.put("Name", profile.getName());
    profileView.put("Username", profile.getUsername());
    profileView.put("Department", profile.getDivision());
    profileView.put("Contract", profile.getContract());
  }

  public String getValueFromMap(String request) {
    return profileView.get(request);
  }

  public HashMap<String, String> createHashMap() {
    return null;
  }
}