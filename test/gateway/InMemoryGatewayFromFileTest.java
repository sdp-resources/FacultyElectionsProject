package gateway;

import fsc.entity.ContractType;
import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InMemoryGatewayFromFileTest {

  private Gateway gateway;
  private String pathname;

  @Before
  public void setUp() {
    pathname = "/data/sample.json";
    gateway = InMemoryGateway.fromJSONFile(pathname);
  }

  @Test
  public void canReadContractTypes() {
    List<String> types = List.of("administrative", "visiting", "tenure-track", "tenured");
    List<ContractType> availableContractTypes = gateway.getAvailableContractTypes();
    List<String> strings = availableContractTypes.stream()
                                                 .map(ContractType::getContract)
                                                 .collect(Collectors.toList());
    assertThat(strings, is(types));
  }

  @Test
  public void canReadDivisions() {
    List<String> divisions = List.of("Arts and Letters", "Humanities", "Natural Sciences",
                                     "Social Sciences");
    for (String division : divisions) {
      assertThat(gateway.getAvailableDivisions(),
                 hasItem(gateway.getEntityFactory().createDivision(division)));
    }
    assertThat(gateway.getAvailableDivisions().size(), is(divisions.size()));
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