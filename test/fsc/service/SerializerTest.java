package fsc.service;

import fsc.entity.Committee;
import fsc.entity.Profile;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.mock.AlwaysTrueQueryStub;
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
  public void profileToString()
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

    assertEquals(expectedOutput, serializer.profileToString(profile));
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

    Profile profile = serializer.stringToProfile(string);

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
          serializer.stringToProfile(serializer.profileToString(originalProfile));

    assertEquals(originalProfile.getName(), generatedProfile.getName());
    assertEquals(originalProfile.getUsername(), generatedProfile.getUsername());
    assertEquals(originalProfile.getDivision(), generatedProfile.getDivision());
    assertEquals(originalProfile.getContract(), generatedProfile.getContract());
  }

  @Test
  public void committeeToString()
  {
    String name = "Steering";
    String description = "Drives the car";

    Committee committee = new Committee(name, description);
    String serializedCommittee = serializer.committeeToString(committee);
    String expectedString = "{\"name\":\"" + name +"\"," +
                                  "\"description\":\"" + description + "\"}";

    assertEquals(expectedString, serializedCommittee);
  }

  @Test
  public void stringToCommittee()
  {
    String name = "Steering";
    String description = "Drives the car";

    String string = "{\"name\":\"" + name +"\"," +
                          "\"description\":\"" + description + "\"}";

    Committee committee = serializer.stringToCommittee(string);

    assertEquals(name, committee.getName());
    assertEquals(description, committee.getDescription());
  }

  @Test
  public void committeeSurvivesRoundTrip()
  {
    String name = "Steering";
    String description = "Drives the car";

    Committee originalCommittee = new Committee(name, description);

    Committee generatedCommittee =
          serializer.stringToCommittee(serializer.committeeToString(originalCommittee));

    assertEquals(originalCommittee.getName(), generatedCommittee.getName());
    assertEquals(originalCommittee.getDescription(), generatedCommittee.getDescription());
  }
}
