package fsc.gateway;


import java.util.ArrayList;

public interface DivisionGateway {
  ArrayList<String> getDivisionList();
  public String getDivision(String divisionName) throws Exception;
  public void addDivision(String divsion);
  public Boolean hasDivision(String divisionName);
}