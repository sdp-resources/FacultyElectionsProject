package fsc.service;

import fsc.entity.Committee;
import fsc.entity.EntityFactory;
import fsc.entity.Profile;
import org.json.JSONObject;

class Serializer {

  private EntityFactory entityFactory;

  Serializer(EntityFactory entityFactory) {
    this.entityFactory = entityFactory;
  }

  String profileToString(Profile profile) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", profile.getName());
    jsonObject.put("username", profile.getUsername());
    jsonObject.put("division", profile.getDivision());
    jsonObject.put("contract", profile.getContract());
    return jsonObject.toString();
  }

  Profile stringToProfile(String string) {
    JSONObject jsonObject = new JSONObject(string);
    String name = toString(jsonObject, "name");
    String username = Serializer.this.toString(jsonObject, "username");
    String division = toString(jsonObject, "division");
    String contract = toString(jsonObject, "contract");
    return entityFactory.createProfile(name, username, division, contract);
  }

  private String toString(JSONObject jsonObject, String username) {
    return jsonObject.getString(username);
  }

  String committeeToString(Committee committee) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", committee.getName());
    jsonObject.put("description", committee.getDescription());
    return jsonObject.toString();
  }

  Committee stringToCommittee(String string) {
    JSONObject jsonObject = new JSONObject(string);
    String name = toString(jsonObject, "name");
    String description = toString(jsonObject, "description");
    return entityFactory.createCommittee(name, description, null);
  }

}
