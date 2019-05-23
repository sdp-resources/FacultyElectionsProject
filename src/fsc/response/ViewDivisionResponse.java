package fsc.response;

import java.util.ArrayList;
import java.util.List;

public class ViewDivisionResponse implements Response{

  public List<String> divisions = new ArrayList<>();


  public ViewDivisionResponse(List<String> divisions){
    this.divisions = divisions;
  }
}
