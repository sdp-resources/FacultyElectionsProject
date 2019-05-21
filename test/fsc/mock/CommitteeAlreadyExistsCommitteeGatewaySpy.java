package fsc.mock;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

public class CommitteeAlreadyExistsCommitteeGatewaySpy implements CommitteeGateway {
  public static String submittedCommitteeName;

  public Committee getCommitteeFromCommitteeName(String name) throws NoCommitteeWithThatNameException {
    submittedCommitteeName = name;
    return null;
  }

  public void addCommittee(Committee committee) {
  }

  public void save() {

  }
}
