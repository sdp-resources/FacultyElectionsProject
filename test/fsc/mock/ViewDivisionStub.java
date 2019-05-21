package fsc.mock;

import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class ViewDivisionStub implements DivisionGateway {

  ArrayList<String> divisionList = new ArrayList<String>();



  public ArrayList<String> getAllDivisions() {
    return divisionList;
  }

  public String getDivision(String divisionName) throws Exception {
    return null;
  }

  public void addDivision(String division) {
    divisionList.add(division);

  }

  public void save() {

  }

  public Boolean hasDivision(String divisionName) {
    return null;
  }
}
