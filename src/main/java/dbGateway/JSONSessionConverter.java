package dbGateway;

import fsc.entity.session.AuthenticatedSession;
import fsc.service.Authorizer;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONSessionConverter {
  public String serialize(AuthenticatedSession session) {
    JSONObject json = new JSONObject();
    json.put("token", session.getToken());
    json.put("expires", session.getExpirationTime());
    json.put("role", session.getRole());
    json.put("username", session.getUsername());
    return json.toString();
  }

  AuthenticatedSession getSession(String s) {
    if (s == null) { return null; }
    try {
      JSONObject sessionJSON = new JSONObject(s);
      String username = sessionJSON.getString("username");
      Authorizer.Role role = Authorizer.Role.valueOf(sessionJSON.getString("role"));
      long expires = sessionJSON.getLong("expires");
      String token = sessionJSON.getString("token");
      return new AuthenticatedSession(role, username, token, expires);
    } catch (JSONException | IllegalArgumentException e) {
      return null;
    }
  }
}
