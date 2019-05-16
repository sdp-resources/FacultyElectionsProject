package fsc.mock;

import fsc.entity.Division;
import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class AddDivisionWhereOneDoesNotAlreadyExistGatewaySpy implements DivisionGateway {

  ArrayList<Division> divisionList = new ArrayList<>();
  private String submittedDivisionName;

  public void AddDivisionWhereOneDoesNotAlreadyExistGatewaySpy(ArrayList<Division> divisionList) {
    this.divisionList = divisionList;
  }

  public void getDivisionWithName(String divisionName) throws Exception {
    submittedDivisionName = divisionName;
  }
}
