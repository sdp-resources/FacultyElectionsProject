package fsc.mock;

import fsc.gateway.ContractsViewerGateway;

import java.util.ArrayList;

public class ContractTypesGatewayStub implements ContractsViewerGateway {

  public ArrayList<String> contracts = new ArrayList<>();

  public ContractTypesGatewayStub() {
    contracts.add("tenure_track");
    contracts.add("tenured");
    contracts.add("admin");
    contracts.add("part-time");
    contracts.add("one-year");
  }

  public ArrayList<String> getContractTypes() {
    return contracts;
  }
}
