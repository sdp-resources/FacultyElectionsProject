package fsc.request;

public class AddContractTypeRequest extends Request {
  public final String contractType;

  public AddContractTypeRequest(String contractType) {
    this.contractType = contractType;
  }

}
