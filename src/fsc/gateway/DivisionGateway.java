package fsc.gateway;


import java.util.ArrayList;

public interface DivisionGateway {
  ArrayList<String> getDivisionList();
  public void getDivision(String divisionName) throws Exception;
  public void addDivision(String divsion);

}