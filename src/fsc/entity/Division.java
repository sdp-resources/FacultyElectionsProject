package fsc.entity;

public class Division {
  private String divisionName;

  Division(String newDivisionName) {
    this.divisionName = newDivisionName;
  }

  String getDivisionName() {
    return divisionName;
  }

  void setDivisionName(String newDivisionName) {
    divisionName = newDivisionName;
  }

}
