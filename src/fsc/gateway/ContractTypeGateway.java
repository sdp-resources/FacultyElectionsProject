package fsc.gateway;

public interface ContractTypeGateway {

  void addContractType(String contract);
  class InvalidContractTypeException extends Exception {}
}


