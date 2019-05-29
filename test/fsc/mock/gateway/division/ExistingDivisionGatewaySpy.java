package fsc.mock.gateway.division;

import java.util.Arrays;
import java.util.List;

public class ExistingDivisionGatewaySpy extends MissingDivisionGatewaySpy {
  public List<String> divisions;
  public String submittedDivisionName;

  public ExistingDivisionGatewaySpy(String... divisions) {
    this.divisions = Arrays.asList(divisions);
  }

  public List<String> getAvailableDivisions() {
    return divisions;
  }

  public void addDivision(String division) {
    super.addDivision(division);
    divisions.add(division);
  }

  public void save() {
    super.save();
  }

  public Boolean hasDivision(String divisionName) {
    super.hasDivision(divisionName);
    submittedDivisionName = divisionName;
    return true;
  }
}