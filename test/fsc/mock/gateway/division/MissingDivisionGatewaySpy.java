package fsc.mock.gateway.division;

import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class MissingDivisionGatewaySpy implements DivisionGateway {
  public String submittedDivisionName;
  public boolean saveCalled = false;

  public ArrayList<String> getAvailableDivisions() {
    return null;
  }

  public void addDivision(String division) { }

  public void save() {
    if (submittedDivisionName != null) {
      saveCalled = true;
    }
  }

  public Boolean hasDivision(String divisionName) {
    submittedDivisionName = divisionName;
    return false;
  }
}
