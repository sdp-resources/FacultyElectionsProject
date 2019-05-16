package fsc.gateway;

public interface ContractTypeGateway {

  String addContractType(String string);
  void getContractTypeFromProfile(String contract_type) throws InvalidContractTypeException;
  class InvalidContractTypeException extends Exception {}
}


