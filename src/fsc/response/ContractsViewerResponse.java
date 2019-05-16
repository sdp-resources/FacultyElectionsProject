package fsc.response;

import java.util.ArrayList;
import java.util.List;

public class ContractsViewerResponse implements Response{
  List<String> contracts;

  public ContractsViewerResponse(List<String> contracts){
    this.contracts = contracts;
  }
}
