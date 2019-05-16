package fsc.response;

import java.util.List;
import java.util.Objects;

public class ContractsViewerResponse implements Response{
  List<String> contracts;

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContractsViewerResponse that = (ContractsViewerResponse) o;
    return Objects.equals(contracts, that.contracts);
  }

  public int hashCode() {
    return Objects.hash(contracts);
  }

  public ContractsViewerResponse(List<String> contracts){
    this.contracts = contracts;
  }
}
