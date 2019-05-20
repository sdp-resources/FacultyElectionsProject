package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

public class AcceptingCommitteeGatewayStub extends CommitteeGatewayDummy {
  Committee committee;

  public AcceptingCommitteeGatewayStub(Committee committee){
    this.committee = committee;
  }

  @Override
  public Committee getCommitteeFromCommitteeName(String name)
  {
    return committee;
  }
}
