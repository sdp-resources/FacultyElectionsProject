package fsc.gateway;

import java.util.List;

public interface ContractTypeGateway {

  void addContractType(String contract) throws Exception;
  List<String> getContractTypes();

  class InvalidContractTypeException extends Exception {}
}


