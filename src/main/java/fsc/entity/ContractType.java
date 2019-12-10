package fsc.entity;

import java.util.Objects;

public class ContractType {
  private String contract;

  public ContractType() {}

  public ContractType(String contract) {
    this.contract = contract;
  }

  public String getContract() {
    return contract;
  }

  public void setContract(String contract) {
    this.contract = contract;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContractType that = (ContractType) o;
    return contract.equals(that.contract);
  }

  public int hashCode() {
    return Objects.hash(contract);
  }
}
