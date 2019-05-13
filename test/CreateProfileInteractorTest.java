import org.junit.Before;
import org.junit.Test;

public class CreateProfileInteractorTest {

  CreateProfileRequest request;

  @Before
  public void setup() {
    CreateProfileRequest request = new CreateProfileRequest("Joe Hayfield", "hayfieldjoe", "ART",
                                                            "Part-Time");
  }

  @Test
  public void testExecute() {
    CreateProfileInteractor.execute(request);
  }
}
