package fsc.entity;

import fsc.entity.Committee;
import fsc.gateway.Gateway;

public class ElectionCreator {

  private Gateway gateway;
  private String committee;
  private String seat;



  public void setGateway(Gateway gateway) {
    this.gateway = gateway;
  }

  public void add(String committee, String seat) {
    this.committee = committee;
    this.seat = seat;
  }
}
