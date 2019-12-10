package fixtures;

import fsc.mock.EntityStub;

public class FscFixture {
  public boolean removeSession() {
    TestContext.app.setSession(null);
    return true;
  }

  public boolean addAdminSession() {
    TestContext.app.setSession(EntityStub.adminSession());
    return true;
  }

  public boolean addUserSession(String username) {
    TestContext.app.setSession(EntityStub.userSession(username));
    return true;
  }

  public boolean addPasswordRecord(String username, String password, String role) {
    return TestContext.app.addPasswordRecord(username, password, role);
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
    return TestContext.app.addContractType(contractType, null);
  }

  public boolean validDivision(String division) {
    return TestContext.app.hasDivision(division);
  }

  public boolean validContractType(String contractType) {
    return TestContext.app.hasContractType(contractType);
  }

  public Long createCommittee(String name, String description, String voterQueryString) {
    return TestContext.app.addCommittee(name, description, voterQueryString);
  }

  public boolean addNamedQuery(String name, String queryString) {
    return TestContext.app.addNamedQuery(name, queryString);
  }

  public Long createSeat(
        Long committeeId, String seatName, String query
  ) {
    return TestContext.app.addSeat(committeeId, seatName, query);
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
