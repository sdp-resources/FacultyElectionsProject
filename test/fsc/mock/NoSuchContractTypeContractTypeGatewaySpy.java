package fsc.mock;

import fsc.gateway.ContractTypeGateway;

public class NoSuchContractTypeContractTypeGatewaySpy implements ContractTypeGateway {
  public String submittedContractType;

  public String addContractType(String contractType) {
    return null;

  }

  public void getContractTypeFromProfile(String contract_type) throws InvalidContractTypeException {
    submittedContractType = contract_type;
    throw new InvalidContractTypeException();

  }
}
