package fsc.mock.gateway.contractType;

import fsc.gateway.ContractTypeGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContractTypesGatewayStub implements ContractTypeGateway {
  public List<String> contractTypes;

  public ContractTypesGatewayStub(String... contractTypes) {
    this.contractTypes = new ArrayList<>(Arrays.asList(contractTypes));
  }

  public void addContractType(String contractType) {
    contractTypes.add(contractType);
  }

  public List<String> getAvailableContractTypes() {
    return contractTypes;
  }
}
