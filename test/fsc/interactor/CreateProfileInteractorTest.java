package fsc.interactor;

import fsc.entity.Profile;
import fsc.request.CreateProfileRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CreateProfileInteractorTest {

  CreateProfileRequest request;
  ArrayList<Profile> profileList;

  public static ArrayList<Profile> generateProfileList() {
    ArrayList<Profile> profiles = new ArrayList<>();
    profiles.add(new Profile("Adam Jones", "jonesa", "SCI", "Tenured"));
    profiles.add(new Profile("Bill Hayfield", "hayfieldb", "ART", "Part-Time"));
    profiles.add(new Profile("Ellen Makhno", "makhnoe", "HUM", "Sabbatical"));
    profiles.add(new Profile("Archie Caulifield", "caulifielda", "SOC", "Untenured"));
    return profiles;
  }

  @Before
  public void setup() {
    CreateProfileRequest request = new CreateProfileRequest("Joe Hayfield", "hayfieldjoe", "ART",
                                                            "Part-Time");
    ArrayList<Profile> profileList = generateProfileList();
  }

  @Test
  public void testExecute() throws Exception {
    profileList = CreateProfileInteractor.execute(request, profileList);
  }
}
