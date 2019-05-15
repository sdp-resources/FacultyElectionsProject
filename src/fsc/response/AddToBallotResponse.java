package fsc.response;

public class AddToBallotResponse {

  public String noBallot() throws RuntimeException {
    return "There is no ballot to add to.";
  }
}
