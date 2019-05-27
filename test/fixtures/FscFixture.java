package fixtures;

public class FscFixture {
  public boolean addProfile(
        String fullname, String username, String contractType, String division
  ) {
    TestContext.addProfile(fullname, username, division, contractType);
    return true;
  }

  public boolean addDivision(String division) {
    return TestContext.addDivision(division);
  }

  public boolean addContractType(String contractType) {
    TestContext.addContractType(contractType);
    return true;
  }

  public boolean validDivision(String division) {
    return TestContext.hasDivision(division);
  }

  public boolean validContractType(String contractType) {
    return TestContext.hasContractType(contractType);
  }
}
