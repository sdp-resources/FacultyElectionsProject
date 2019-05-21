package fsc.gateway;

public interface ContractTypeGateway {

  void addContractType(String contract) throws Exception;
  class InvalidContractTypeException extends Exception {}
}


