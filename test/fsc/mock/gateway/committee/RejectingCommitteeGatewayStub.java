package fsc.mock.gateway.committee;

import fsc.entity.Committee;

public class RejectingCommitteeGatewayStub extends CommitteeGatewayDummy {
  @Override
  public Committee getCommitteeFromCommitteeName(String name) throws UnknownCommitteeException
  {
    throw new UnknownCommitteeException();
  }
}
