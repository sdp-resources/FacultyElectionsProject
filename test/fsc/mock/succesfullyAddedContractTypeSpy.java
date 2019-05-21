package fsc.mock;

import fsc.gateway.ContractTypeGateway;

public class succesfullyAddedContractTypeSpy implements ContractTypeGateway {
  public String submittedContractType;

  public void addContractType(String contractType) {
    submittedContractType = contractType;
  }
}
