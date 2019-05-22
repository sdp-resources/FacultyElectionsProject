package gateway;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InMemoryGatewayFromFileTest {

  private File file;
  private InMemoryGateway gateway;

  @Before
  public void setUp() throws Exception {
    file = new File("assets/data/sample.json");
    gateway = InMemoryGateway.fromFile(file);
  }

  @Test
  public void canReadFile() {
    System.out.println(file.getAbsolutePath());
    assertTrue(file.exists());
    assertTrue(file.canRead());
  }

  @Test
  public void canReadContractTypes() {
    List<String> types = List.of("administrative", "visiting", "tenure-track", "tenured");
    assertThat(gateway.getContractTypes(), is(types));
  }

  @Test
  public void canDivisions() {
    List<String> divisions = List.of("Arts and Letters", "Humanities", "Natural Sciences", "Social Sciences");
    for (String type : divisions) {
      assertThat(gateway.getAllDivisions(), hasItem(type));
    }
    assertThat(gateway.getAllDivisions(), is(divisions));
  }

}