
package fsc.mock;

import fsc.entity.Division;
import fsc.gateway.DivisionGateway;

import java.util.ArrayList;
import java.util.List;

public class ExistingDivisionGatewaySpy implements DivisionGateway {
  ArrayList<Division> divisionList;
  public String submittedDivisionName;

  public void ExistingDivisionGatewaySpy() {
    this.divisionList = null;
  }

  public List<String> getAllDivisions() {
    return null;
  }

  public String getDivision(String divisionName) throws Exception {
    return divisionName;
  }

  public void addDivision(String division) {
  }

  public void save() {

  }

  public Boolean hasDivision(String divisionName) {
    submittedDivisionName = divisionName;
    return true;
  }
}