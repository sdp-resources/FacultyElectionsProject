package fsc.mock;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

public class CommitteeAlreadyExistsCommitteeGatewaySpy implements CommitteeGateway {
  public static String submittedCommitteeName;

  public Committee getCommitteeFromCommitteeName(String name) throws Exception {
    submittedCommitteeName = name;
    return null;
  }

  public void addCommittee(Committee makeCommitteeFromRequest) {
  }
}