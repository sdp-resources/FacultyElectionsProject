package fsc.mock;

import fsc.entity.Division;
import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class AddDivisionWhereOneDoesNotAlreadyExistGatewaySpy implements DivisionGateway {
  ArrayList<Division> divisionList;
  public String submittedDivisionName;

  public void AddDivisionWhereOneDoesNotAlreadyExistGatewaySpyArrayList() {
    this.divisionList = null;
  }

  public void getDivisionWithName(String divisionName) throws Exception {
    submittedDivisionName = divisionName;
    throw new Exception("No Division With That name Found");
  }
}