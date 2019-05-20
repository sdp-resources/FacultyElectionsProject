package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

public class RejectingCommitteeGatewayStub extends CommitteeGatewayDummy {
  @Override
  public Committee getCommitteeFromCommitteeName(String name) throws NoCommitteeWithThatNameException
  {
    throw new CommitteeGateway.NoCommitteeWithThatNameException();
  }
}
