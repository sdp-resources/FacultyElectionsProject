package fsc.gateway;


import java.util.List;

public interface DivisionGateway {
  List<String> getAllDivisions();
  public String getDivision(String divisionName) throws Exception;
  public void addDivision(String divsion);
  public Boolean hasDivision(String divisionName);
}