package fsc.gateway;

import java.util.List;

public interface ContractTypeGateway {

  void addContractType(String contract) throws ExistingContractTypeException;
  List<String> getAvailableContractTypes();

  class ExistingContractTypeException extends Exception {}
}


