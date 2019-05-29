package fsc.mock.gateway.contractType;

import fsc.gateway.ContractTypeGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContractTypesGatewayStub implements ContractTypeGateway {
  public List<String> contractTypes;
  public List<String> events = new ArrayList<>();

  public ContractTypesGatewayStub(String... contractTypes) {
    this.contractTypes = new ArrayList<>(Arrays.asList(contractTypes));
  }

  public void addContractType(String contractType) {
    events.add("add contract type: " + contractType);
    contractTypes.add(contractType);
  }

  public List<String> getAvailableContractTypes() {
    return contractTypes;
  }

  public void save() {
    events.add("save");
  }

  public boolean hasContractType(String contract) {
    events.add("has contract type: " + contract);
    return contractTypes.contains(contract);
  }
}
