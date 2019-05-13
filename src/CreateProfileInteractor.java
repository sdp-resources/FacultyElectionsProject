import java.util.ArrayList;

public class CreateProfileInteractor {

  public static void execute(CreateProfileRequest request, ArrayList<Profile> profiles)
        throws Exception {
    if (InMemoryGateway.getProfileWitheUsername(request.username, profiles) != null) {
      throw new Exception("UsernameAlreadyUsed");
    }

  }


}
