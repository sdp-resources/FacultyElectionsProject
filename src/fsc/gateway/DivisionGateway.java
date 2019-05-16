package fsc.gateway;


import java.util.ArrayList;

public interface DivisionGateway {
  ArrayList<String> getDivisionList();
  public void getDivisionWithName(String divisionName) throws Exception;
  public void addDivision(String divsion);

}