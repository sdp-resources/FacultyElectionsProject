package fsc.mock.gateway.division;

import fsc.entity.Division;
import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;

import java.util.ArrayList;
import java.util.List;

public class ExistingDivisionGatewaySpy extends MissingDivisionGatewaySpy {
  private final EntityFactory entityFactory = new SimpleEntityFactory();
  public List<Division> divisions = new ArrayList<>();
  public String submittedDivisionName;

  public ExistingDivisionGatewaySpy(String... divisions) {
    for (String division : divisions) {
      addDivision(entityFactory.createDivision(division));
    }
  }

  public List<Division> getAvailableDivisions() {
    return divisions;
  }

  public void addDivision(Division division) {
    super.addDivision(division);
    divisions.add(division);
  }

  public Boolean hasDivision(String divisionName) {
    super.hasDivision(divisionName);
    submittedDivisionName = divisionName;
    return true;
  }
}