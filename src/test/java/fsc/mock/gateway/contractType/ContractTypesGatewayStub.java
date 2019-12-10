package fsc.mock.gateway.contractType;

import fsc.entity.ContractType;
import fsc.gateway.ContractTypeGateway;

import java.util.ArrayList;
import java.util.List;

public class ContractTypesGatewayStub implements ContractTypeGateway {
  public List<ContractType> contractTypes = new ArrayList<>();
  public List<String> events = new ArrayList<>();

  public ContractTypesGatewayStub(String... contractTypes) {
    for (String contractType : contractTypes) {
      this.contractTypes.add(new ContractType(contractType));
    }
  }

  public void addContractType(ContractType contractType) {
    events.add("add contract type: " + contractType.getContract());
    contractTypes.add(contractType);
  }

  public List<ContractType> getAvailableContractTypes() {
    return contractTypes;
  }

  public void save() {
    events.add("save");
  }

  public boolean hasContractType(String contract) {
    events.add("has contract type: " + contract);
    return contractTypes.contains(new ContractType(contract));
  }
}
