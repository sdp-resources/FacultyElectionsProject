package fsc.response;

import java.util.List;

public class ContractsViewerResponse implements Response{
  List<String> contracts;
  public void ContractsViewerResonse(List<String> contracts){
    this.contracts = contracts;
  }
}
