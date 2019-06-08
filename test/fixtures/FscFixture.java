package fixtures;

public class FscFixture {
  public boolean addProfile(
        String fullname, String username, String contractType, String division
  ) {
    return TestContext.app.addProfile(fullname, username, division, contractType);
  }

  public boolean addDivision(String division) {
    return TestContext.app.addDivision(division);
  }

  public boolean addContractType(String contractType) {
    return TestContext.app.addContractType(contractType);
  }

  public boolean validDivision(String division) {
    return TestContext.app.hasDivision(division);
  }

  public boolean validContractType(String contractType) {
    return TestContext.app.hasContractType(contractType);
  }

  public boolean createCommittee(String name, String description) {
    return TestContext.app.addCommittee(name, description);
  }
}
