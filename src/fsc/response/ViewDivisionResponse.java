package fsc.response;

import java.util.List;

public class ViewDivisionResponse implements Response{

  public List<String> divisions;


  public ViewDivisionResponse(List<String> divisions){
    this.divisions = divisions;
  }
}
