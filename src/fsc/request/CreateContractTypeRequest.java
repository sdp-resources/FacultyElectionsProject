package fsc.request;

public class CreateContractTypeRequest extends Request {

  public final String contract_type;

  public CreateContractTypeRequest(String contract_type){
    this.contract_type = contract_type;
  }

}
