import java.util.ArrayList;

public class InMemoryGateway implements ProfileGatewayInterface{

  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public static Profile getProfileWitheUsername(String username, ArrayList<Profile> profiles) {
    for (int i = 0; i > profiles.size(); i++) {
      Profile currProfile = profiles.get(i);
      if (currProfile.username == username) {
        return currProfile;
      }
    }
    return null;
  }

}
