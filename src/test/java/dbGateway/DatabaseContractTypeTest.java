package dbGateway;

import fsc.entity.ContractType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseContractTypeTest extends BasicDatabaseTest {
  private ContractType contractType;

  @Test
  public void canCreateAContractType() {
    saveTheContractType();
    List<ContractType> contractTypes = anotherGateway.getAvailableContractTypes();
    assertEquals(1, contractTypes.size());
    assertEquals(contractType, contractTypes.get(0));
    assertEquals(true, anotherGateway.hasContractType(contractType.getContract()));
  }

  private void saveTheContractType() {
    contractType = gateway.getEntityFactory()
                          .createContractType("tenured");
    gateway.addContractType(contractType);
    gateway.commit();
  }

}
