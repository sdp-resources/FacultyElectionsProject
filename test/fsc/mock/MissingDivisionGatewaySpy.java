package fsc.mock;

import fsc.entity.Division;
import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class MissingDivisionGatewaySpy implements DivisionGateway {
  final ArrayList<Division> divisionList ;
  public String submittedDivisionName;

  public MissingDivisionGatewaySpy() {
    this.divisionList = null;
  }

  public ArrayList<String> getAvailableDivisions() {
    return null;
  }

  public void addDivision(String division) {}

  public void save() {}

  public Boolean hasDivision(String divisionName) {
    submittedDivisionName = divisionName;
    return false;
  }
}
