package committee;

import committee.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryTest {

  private Query query;
  private Profile profile;

  @Before
  public void setUp() throws Exception {
    profile = new Profile("Todd", "SmithT", "Art", "contract");
  }

  @Test
  public void ProfileIsAlwaysFalse(){
    query = new AlwaysFalseQuery();
    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void ProfileIsAlwaysTrue(){
    query = new AlwaysTrueQuery();
    assertTrue(query.isProfileValid(profile));
  }
}