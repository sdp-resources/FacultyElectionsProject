import java.util.ArrayList;

public class CreateProfileInteractor {

  public static ArrayList<Profile> execute(CreateProfileRequest request,
                                           ArrayList<Profile> profiles)
        throws Exception {
    if (InMemoryGateway.getProfileWitheUsername(request.username, profiles) != null) {
      throw new Exception("UsernameAlreadyUsed");
    }
    Profile profile = new Profile(request.name, request.username, request.department,
                                  request.contract);
    profiles.add(profile);
    return profiles;
  }


}
