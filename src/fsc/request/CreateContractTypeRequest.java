package fsc.request;

public class CreateContractTypeRequest extends Request {
  public final String contractType;

  public CreateContractTypeRequest(String contractType) {
    this.contractType = contractType;
  }

}
