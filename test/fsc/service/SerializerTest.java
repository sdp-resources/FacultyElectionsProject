package fsc.service;

import fsc.entity.Profile;
import fsc.entity.Seat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
  Serializer serializer;

  @Before
  public void Setup()
  {
    serializer = new Serializer();
  }

  @Test
  public void profileToStringGivesExpectedString()
  {
    String name = "Joe Average";
    String username = "averagej";
    String division = "ART";
    String contract = "Tenured";

    Profile profile = new Profile(name, username, division, contract);


    String expectedOutput = "{\"division\":\"" + division +"\"," +
                                  "\"contract\":\"" + contract + "\"," +
                                  "\"name\":\"" + name + "\"," +
                                  "\"username\":\"" + username + "\"}";

    assertEquals(expectedOutput, serializer.ProfileToString(profile));
  }

  @Test
  public void formattedStringGivesExpectedProfile()
  {
    String name = "Joe Average";
    String username = "averagej";
    String division = "ART";
    String contract = "Tenured";

    String string = "{\"name\":\"" + name +"\"," +
                          "\"username\":\"" + username + "\"," +
                          "\"division\":\"" + division+ "\"," +
                          "\"contract\":\"" + contract + "\"}";

    Profile profile = serializer.StringToProfile(string);

    assertEquals(name, profile.getName());
    assertEquals(username, profile.getUsername());
    assertEquals(division, profile.getDivision());
    assertEquals(contract, profile.getContract());
  }

  @Test
  public void profileSurvivesRoundTrip()
  {
    String name = "Joe Average";
    String username = "averagej";
    String division = "ART";
    String contract = "Tenured";

    Profile originalProfile = new Profile(name, username, division, contract);

    Profile generatedProfile =
          serializer.StringToProfile(serializer.ProfileToString(originalProfile));

    assertEquals(originalProfile.getName(), generatedProfile.getName());
    assertEquals(originalProfile.getUsername(), generatedProfile.getUsername());
    assertEquals(originalProfile.getDivision(), generatedProfile.getDivision());
    assertEquals(originalProfile.getContract(), generatedProfile.getContract());
  }
}
