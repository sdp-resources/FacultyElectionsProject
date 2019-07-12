package fsc.gateway;

import fsc.entity.ContractType;

import java.util.List;

public interface ContractTypeGateway {

  void addContractType(ContractType contractType);
  List<ContractType> getAvailableContractTypes();
  void save();
  boolean hasContractType(String contract);
}


