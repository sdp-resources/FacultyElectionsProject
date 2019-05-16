package fsc.request;

import fsc.response.CreateCommitteeResponse;

public class CreateCommitteeRequest {
  public String name;
  public String description;
  public CreateCommitteeRequest(String name, String description){
    this.name = name;
    this.description = description;
  }
}
