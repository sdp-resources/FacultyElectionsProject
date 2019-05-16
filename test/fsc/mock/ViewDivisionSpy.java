package fsc.mock;

import fsc.gateway.DivisionGateway;

import java.util.ArrayList;

public class ViewDivisionSpy implements DivisionGateway {

  ArrayList<String> divisionList = new ArrayList<String>();



  public ArrayList<String> getDivisionList() {
    return divisionList;
  }

  public void getDivisionWithName(String divisionName) throws Exception {

  }

  public void addDivision(String divsion) {
    divisionList.add(divsion);

  }
}
