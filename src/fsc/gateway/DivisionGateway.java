package fsc.gateway;


import java.util.List;

public interface DivisionGateway {
  List<String> getAllDivisions();
  String getDivision(String divisionName) throws Exception;
  void addDivision(String division);
  void save();
  Boolean hasDivision(String divisionName);
}