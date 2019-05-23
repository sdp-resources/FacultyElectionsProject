package fsc.response;

import java.util.List;
import java.util.Objects;

public class ContractsViewerResponse implements Response{
  public List<String> contracts;

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewContractsResponse that = (ViewContractsResponse) o;
    return Objects.equals(contracts, that.contracts);
  }

  public int hashCode() {
    return Objects.hash(contracts);
  }

  public ViewContractsResponse(List<String> contracts){
    this.contracts = contracts;
  }
}
