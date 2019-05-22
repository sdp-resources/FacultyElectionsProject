package fsc.mock;

import fsc.gateway.ContractTypeGateway;

import java.util.ArrayList;
import java.util.List;

public class CreatContractTypeGateWaySpy implements ContractTypeGateway {
  public List<String> curentContractTypes = new ArrayList<String>();

  public CreatContractTypeGateWaySpy(){
    curentContractTypes.add("sabbatical");
  }

  private boolean hasContractType(String contractType){
    return curentContractTypes.contains(contractType);
  }

  public void addContractType(String contractType) throws Exception{
    if (hasContractType(contractType)){
      throw new InvalidContractTypeException();
    }
    curentContractTypes.add(contractType);
  }

  public List<String> getAvailableContractTypes() {
    return null;
  }
}
