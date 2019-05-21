
package fsc.mock;

import fsc.entity.Division;
import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class DivisionWithThatNameAlreadyExistsGatewaySpy implements DivisionGateway {
  ArrayList<Division> divisionList;
  public String submittedDivisionName;

  public void DivisionWithThatNameAlreadyExistsGatewaySpy() {
    this.divisionList = null;
  }

  public ArrayList<String> getDivisionList() {
    return null;
  }

  public void getDivisionWithName(String divisionName) throws Exception {
    submittedDivisionName = divisionName;
  }

  public void addDivision(String division) {
  }
}