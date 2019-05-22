package fsc.gateway;


import java.util.List;

public interface DivisionGateway {
  List<String> getAllDivisions();
  void addDivision(String division);
  void save();
  Boolean hasDivision(String divisionName);
}