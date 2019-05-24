package fsc.mock;

import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class ViewDivisionStub implements DivisionGateway {

  final ArrayList<String> divisionList = new ArrayList<>();

  public ArrayList<String> getAvailableDivisions() {
    return divisionList;
  }

  public void addDivision(String division) {
    divisionList.add(division);
  }

  public void save() {}

  public Boolean hasDivision(String divisionName) {
    return null;
  }
}
