package fsc.interactor;

import fsc.entity.Profile;

import java.util.*;

public class ProfileViewer {
  Map<String, String> profileView = new HashMap<String, String>();

  public ProfileViewer(Profile profile) {
    profileView.put("Name", profile.getName());
    profileView.put("Username", profile.getUsername());
    profileView.put("Department", profile.getDepartment());
    profileView.put("Contract", profile.getContract());
  }

  public String getValueFromMap(String request) {
    return profileView.get(request);
  }
}