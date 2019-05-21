package gateway;

import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;

public class InMemoryGateway implements ProfileGateway, Gateway {

  public ArrayList<Profile> profiles = new ArrayList<>();

  public InMemoryGateway() {
    this.profiles = new ArrayList<>();
    profiles.add(new Profile("Haris", "skiadas", "Math", "tenured"));
    profiles.add(new Profile("Theresa", "wilson", "CS", "tenured"));
  }

  public ArrayList<Profile> getAllProfiles() {
    return profiles;
  }

  public Profile getProfileFromUsername(String username) {
    for (Profile currProfile : profiles) {
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    throw new RuntimeException("No Profile With that Username");
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void saveProfile(Profile profile) {

  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    return (currProfile.username.equals(username));

  }

  public String addContractType(String string) {
    return null;
  }

  public void getContractTypeFromProfile(String contract_type) throws InvalidContractTypeException {

  }
}
