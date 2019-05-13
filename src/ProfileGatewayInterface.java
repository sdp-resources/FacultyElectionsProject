import java.util.ArrayList;

public interface ProfileGatewayInterface {
  public ArrayList<Profile> getProfiles();
  public Profile getProfileWitheUsername(String username);
}
