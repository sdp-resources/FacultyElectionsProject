import java.util.ArrayList;
import java.util.List;

public class Ballot {

  private List<Profile> profiles = new ArrayList<>();

  public boolean isEmpty() {
    return profiles.isEmpty();
  }

  public void add(Profile profile) {
    profiles.add(profile);
  }
}
