package fsc.mock.gateway.division;

import fsc.gateway.DivisionGateway;

import java.util.Arrays;
import java.util.List;

public class ExistingDivisionGatewaySpy implements DivisionGateway {
  public List<String> divisions;
  public String submittedDivisionName;

  public ExistingDivisionGatewaySpy(String... divisions) {
    this.divisions = Arrays.asList(divisions);
  }

  public List<String> getAvailableDivisions() {
    return divisions;
  }

  public void addDivision(String division) {
    divisions.add(division);
  }

  public void save() {

  }

  public Boolean hasDivision(String divisionName) {
    submittedDivisionName = divisionName;
    return true;
  }
}