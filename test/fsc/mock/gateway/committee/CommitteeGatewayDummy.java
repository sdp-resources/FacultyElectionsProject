package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

public class CommitteeGatewayDummy implements CommitteeGateway {
  public Committee getCommitteeFromCommitteeName(String name)
        throws NoCommitteeWithThatNameException {
    return null;
  }

  public void addCommittee(Committee makeCommitteeFromRequest) {

  }
}
