package fsc.mock;

import fsc.gateway.ContractTypeGateway;

public class NoSuchContractTypeContractTypeGatewaySpy implements ContractTypeGateway {
  public String submittedContractType;

  public void addContractType(String contractType) { }

  public void getContractTypeFromProfile(String contract_type) throws InvalidContractTypeException {
    submittedContractType = contract_type;
    throw new InvalidContractTypeException();

  }
}
