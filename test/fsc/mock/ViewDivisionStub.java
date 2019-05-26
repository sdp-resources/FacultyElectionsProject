package fsc.mock;

import fsc.gateway.DivisionGateway;

import java.util.Arrays;
import java.util.List;

public class ViewDivisionStub implements DivisionGateway {
  public List<String> divisions;

  public ViewDivisionStub(String... divisions) {
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
    return null;
  }
}
