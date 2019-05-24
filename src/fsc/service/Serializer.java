package fsc.service;

import fsc.entity.Committee;
import fsc.entity.Profile;
import fsc.entity.Seat;
import org.json.JSONObject;

class Serializer {

  String profileToString(Profile profile)
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", profile.getName());
    jsonObject.put("username", profile.getUsername());
    jsonObject.put("division", profile.getDivision());
    jsonObject.put("contract", profile.getContract());
    return jsonObject.toString();
  }

  Profile stringToProfile(String string)
  {
    JSONObject jsonObject = new JSONObject(string);
    String name = jsonObject.getString("name");
    String username = jsonObject.getString("username");
    String division = jsonObject.getString("division");
    String contract = jsonObject.getString("contract");
    return new Profile(name, username, division, contract);
  }

  String committeeToString(Committee committee) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", committee.getName());
    jsonObject.put("description", committee.getDescription());
    return jsonObject.toString();
  }

  Committee stringToCommittee(String string) {
    JSONObject jsonObject = new JSONObject(string);
    String name = jsonObject.getString("name");
    String description = jsonObject.getString("description");
    return new Committee(name, description);
  }

}
