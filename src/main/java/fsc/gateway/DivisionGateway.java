package fsc.gateway;

import fsc.entity.Division;

import java.util.List;

public interface DivisionGateway {
  List<Division> getAvailableDivisions();
  void addDivision(Division division);
  void save();
  Boolean hasDivision(String divisionName);
}