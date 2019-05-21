
package fsc.mock;

import fsc.entity.Division;
import fsc.gateway.DivisionGateway;

import java.util.ArrayList;
import java.util.List;

public class DivisionWithThatNameAlreadyExistsGatewaySpy implements DivisionGateway {
  ArrayList<Division> divisionList;
  public String submittedDivisionName;

  public void DivisionWithThatNameAlreadyExistsGatewaySpy() {
    this.divisionList = null;
  }

  public List<String> getAllDivisions() {
    return null;
  }

  public void getDivision(String divisionName) throws Exception {
    submittedDivisionName = divisionName;
  }

  public void addDivision(String division) {
  }
}