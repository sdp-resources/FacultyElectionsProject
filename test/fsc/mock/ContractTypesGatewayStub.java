package fsc.mock;

import fsc.gateway.ContractTypeGateway;

import java.util.ArrayList;

public class ContractTypesGatewayStub implements ContractTypeGateway {

  public ArrayList<String> contracts = new ArrayList<>();

  public ContractTypesGatewayStub() {
    contracts.add("tenure_track");
    contracts.add("tenured");
    contracts.add("admin");
    contracts.add("part-time");
    contracts.add("one-year");
  }

  public void addContractType(String contract) throws Exception {

  }

  public ArrayList<String> getAvailableContractTypes() {
    return contracts;
  }
}
