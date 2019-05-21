package fsc.gateway;

public interface ContractTypeGateway {

  void addContractType(String contract);
  void getContractTypeFromProfile(String contract_type) throws InvalidContractTypeException;
  class InvalidContractTypeException extends Exception {}
}


