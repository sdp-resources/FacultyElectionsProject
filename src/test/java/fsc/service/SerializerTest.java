package fsc.service;

import fsc.entity.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
  private Serializer serializer;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    serializer = new Serializer(entityFactory);
  }

  @Test
  public void profileToString() {
    String name = "Joe Average";
    String username = "averagej";
    String division = "ART";
    String contract = "Tenured";

    Profile profile = entityFactory.createProfile(name, username, division, contract);

    String expectedOutput = "{\"division\":\"" + division + "\"," + "\"contract\":\"" + contract + "\"," + "\"name\":\"" + name + "\"," + "\"username\":\"" + username + "\"}";

    assertEquals(expectedOutput, serializer.profileToString(profile));
  }

  @Test
  public void formattedStringGivesExpectedProfile() {
    String name = "Joe Average";
    String username = "averagej";
    String division = "ART";
    String contract = "Tenured";

    String string = "{\"name\":\"" + name + "\"," + "\"username\":\"" + username + "\"," + "\"division\":\"" + division + "\"," + "\"contract\":\"" + contract + "\"}";

    Profile profile = serializer.stringToProfile(string);

    assertEquals(name, profile.getName());
    assertEquals(username, profile.getUsername());
    assertEquals(division, profile.getDivision());
    assertEquals(contract, profile.getContract());
  }

  @Test
  public void profileSurvivesRoundTrip() {
    String name = "Joe Average";
    String username = "averagej";
    String division = "ART";
    String contract = "Tenured";

    Profile originalProfile = entityFactory
                                    .createProfile(name, username, division, contract);

    Profile generatedProfile = serializer
                                     .stringToProfile(serializer.profileToString(originalProfile));

    assertEquals(originalProfile.getName(), generatedProfile.getName());
    assertEquals(originalProfile.getUsername(), generatedProfile.getUsername());
    assertEquals(originalProfile.getDivision(), generatedProfile.getDivision());
    assertEquals(originalProfile.getContract(), generatedProfile.getContract());
  }

  @Test
  public void committeeToString() {
    String name = "Steering";
    String description = "Drives the car";

    Committee committee = entityFactory.createCommittee(name, description, null);
    String serializedCommittee = serializer.committeeToString(committee);
    String expectedString = "{\"name\":\"" + name + "\"," + "\"description\":\"" + description + "\"}";

    assertEquals(expectedString, serializedCommittee);
  }

  @Test
  public void stringToCommittee() {
    String name = "Steering";
    String description = "Drives the car";

    String string = "{\"name\":\"" + name + "\"," + "\"description\":\"" + description + "\"}";

    Committee committee = serializer.stringToCommittee(string);

    assertEquals(name, committee.getName());
    assertEquals(description, committee.getDescription());
  }

  @Test
  public void committeeSurvivesRoundTrip() {
    String name = "Steering";
    String description = "Drives the car";

    Committee originalCommittee = entityFactory.createCommittee(name, description, null);

    Committee generatedCommittee = serializer.stringToCommittee(
          serializer.committeeToString(originalCommittee));

    assertEquals(originalCommittee.getName(), generatedCommittee.getName());
    assertEquals(originalCommittee.getDescription(), generatedCommittee.getDescription());
  }
}
