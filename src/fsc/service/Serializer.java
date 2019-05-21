package fsc.service;

import fsc.entity.Profile;
import fsc.entity.Seat;
import org.json.JSONObject;

public class Serializer {
  public String SeatToString(Seat seat)
  {
    return "";
  }

  public String ProfileToString(Profile profile)
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", profile.getName());
    jsonObject.put("username", profile.getUsername());
    jsonObject.put("division", profile.getDivision());
    jsonObject.put("contract", profile.getContract());
    return jsonObject.toString();
  }

  public Profile StringToProfile(String string)
  {
    JSONObject jsonObject = new JSONObject(string);
    String name = jsonObject.getString("name");
    String username = jsonObject.getString("username");
    String division = jsonObject.getString("division");
    String contract = jsonObject.getString("contract");
    return new Profile(name, username, division, contract);
  }
}
