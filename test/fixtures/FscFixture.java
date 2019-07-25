package fixtures;

import fsc.mock.EntityStub;

public class FscFixture {
  public boolean addAdminSession() {
    TestContext.app.setSession(EntityStub.adminSession());
    return true;
  }

  public boolean addUserSession(String username) {
    TestContext.app.setSession(EntityStub.userSession(username));
    return true;
  }

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

  public boolean createCommittee(String name, String description, String voterQueryString) {
    return TestContext.app.addCommittee(name, description, voterQueryString);
  }

  public boolean addNamedQuery(String name, String queryString) {
    return TestContext.app.addNamedQuery(name, queryString);
  }

  public boolean createSeat(String committeeName, String seatName, String query) {
    return TestContext.app.addSeat(committeeName, seatName, query);
  }

  public boolean canRequestProfile(String username) {
    try {
      TestContext.app.getProfile(username);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  public void closeContext() {
    TestContext.app.shutdown();
  }
}
