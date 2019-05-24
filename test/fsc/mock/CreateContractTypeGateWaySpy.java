package fsc.mock;

import fsc.gateway.ContractTypeGateway;

import java.util.ArrayList;
import java.util.List;

public class CreateContractTypeGateWaySpy implements ContractTypeGateway {
  public final List<String> currentContractTypes = new ArrayList<>();

  public CreateContractTypeGateWaySpy(){
    currentContractTypes.add("sabbatical");
  }

  private boolean hasContractType(String contractType){
    return currentContractTypes.contains(contractType);
  }

  public void addContractType(String contractType) throws Exception{
    if (hasContractType(contractType)){
      throw new InvalidContractTypeException();
    }
    currentContractTypes.add(contractType);
  }

  public List<String> getAvailableContractTypes() {
    return null;
  }
}
