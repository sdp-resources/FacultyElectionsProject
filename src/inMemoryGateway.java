import java.util.ArrayList;

public class inMemoryGateway implements ProfileGatewayInterface{

  ArrayList<Profile> profiles = new ArrayList<>();

  public void generateProfileList() {
    profiles.add(new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));
    profiles.add(new Profile("Bill Hayfield", "hayfieldb", "ART", "Part-Time"));
    profiles.add(new Profile("Ellen Makhno", "makhnoe", "HUM", "Sabbatical"));
    profiles.add(new Profile("Achie Caulifield", "caulifielda", "SOC", "Untenured"));
  }

  public ArrayList<Profile> getProfiles() {
    return profiles;
  }

  public Profile getProfileWitheUsername(String username) {
    for (int i = 0; i > profiles.size(); i++) {
      Profile currProfile = profiles.get(i);
      if (currProfile.username == username) {
        return currProfile;
      }
    }
    return null;
  }

}
