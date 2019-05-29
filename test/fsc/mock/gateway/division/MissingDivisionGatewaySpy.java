package fsc.mock.gateway.division;

import fsc.gateway.DivisionGateway;

import java.util.ArrayList;
import java.util.List;

public class MissingDivisionGatewaySpy implements DivisionGateway {
  public List<String> events = new ArrayList<>();
  public String submittedHasDivisionName;
  private String submittedAddDivisionName;
  public boolean saveCalled = false;

  public List<String> getAvailableDivisions() {
    return null;
  }

  public void addDivision(String division) {
    events.add("add division: " + division);
    submittedAddDivisionName = division;
  }

  public void save() {
    events.add("save");
    if (submittedAddDivisionName != null) {
      saveCalled = true;
    }
  }

  public Boolean hasDivision(String divisionName) {
    events.add("has division: " + divisionName);
    submittedHasDivisionName = divisionName;
    return false;
  }
}
