package fsc.gateway;

import java.util.List;

public interface ContractTypeGateway {

  void addContractType(String contract);
  List<String> getAvailableContractTypes();
  void save();
  boolean hasContractType(String contract);
}


