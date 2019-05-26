package fsc.service;

import fsc.entity.Profile;

import java.util.HashMap;
import java.util.Map;

public class ProfileToHashMapConverter {
  public Map<String, String> createHashMap(Profile profile) {
    Map<String, String> profileView = new HashMap<>();
    profileView.put("Username", profile.getUsername());
    profileView.put("Name", profile.getName());
    profileView.put("Department", profile.getDivision());
    profileView.put("Contract", profile.getContract());
    return profileView;
  }
}