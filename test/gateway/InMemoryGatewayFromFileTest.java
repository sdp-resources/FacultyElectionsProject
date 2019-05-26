package gateway;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InMemoryGatewayFromFileTest {

  private InMemoryGateway gateway;
  private String pathname;

  @Before
  public void setUp() {
    pathname = "assets/data/sample.json";
    gateway = InMemoryGateway.fromJSONFile(pathname);
  }

  @Test
  public void canReadContractTypes() {
    List<String> types = List.of("administrative", "visiting", "tenure-track", "tenured");
    assertThat(gateway.getAvailableContractTypes(), is(types));
  }

  @Test
  public void canReadDivisions() {
    List<String> divisions = List.of("Arts and Letters", "Humanities", "Natural Sciences",
                                     "Social Sciences");
    for (String type : divisions) {
      assertThat(gateway.getAvailableDivisions(), hasItem(type));
    }
    assertThat(gateway.getAvailableDivisions(), is(divisions));
  }

  @Test
  public void canReadProfiles() throws ProfileGateway.InvalidProfileUsernameException {
    Profile skiadas = gateway.getProfile("skiadas");
    assertThat(skiadas.getName(), is("Haris Skiadas"));
    assertThat(skiadas.getDivision(), is("Natural Sciences"));
    assertThat(skiadas.getContract(), is("tenured"));
    Profile kate = gateway.getProfile("johnsonk");
    assertThat(kate.getName(), is("Kate Johnson"));
    assertThat(kate.getDivision(), is("Humanities"));
    assertThat(kate.getContract(), is("tenured"));
  }
}