package fsc.gateway;

import java.util.List;

public interface ContractTypeGateway {

  void addContractType(String contract) throws Exception;
  List<String> getAvailableContractTypes();

  class InvalidContractTypeException extends Exception {}
}


