package fsc.mock.gateway.committee;

import fsc.entity.Committee;

public class RejectingCommiteeGatewaySpy extends CommitteeGatewayDummy {
  public String committeeNameRequested = null;
  public Committee committeeAdded = null;

  @Override
  public Committee getCommitteeFromCommitteeName(String name) throws UnknownCommitteeException
  {
    committeeNameRequested = name;
    throw new UnknownCommitteeException();
  }

  @Override
  public void addCommittee(Committee committee)
  {
    committeeAdded = committee;
    super.addCommittee(committee);
  }
}
