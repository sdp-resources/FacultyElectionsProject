import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class QueryTest {

  @Test
  public void createQuery(){
    Query query = new Query();
  }

  @Test
  public void ProfileIsAlwaysFalse(){
    Query query = new Query();
    Profile profile = new Profile("Todd", "Art");
    assertFalse(query.isProfileValid(profile));

  }

}