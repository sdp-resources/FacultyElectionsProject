package fsc.mock.gateway.committee;

import fsc.entity.Committee;

import java.util.List;

public class AcceptingCommitteeGatewaySpy extends AcceptingCommitteeGatewayStub {
  public List<String> committeeNamesRequested;
  public List<Committee> committeesAdded;

  public AcceptingCommitteeGatewaySpy(Committee committee)
  {
    super(committee);
  }

  @Override
  public Committee getCommitteeFromCommitteeName(String name)
  {
    committeeNamesRequested.add(name);
    return super.getCommitteeFromCommitteeName(name);
  }

  @Override
  public void addCommittee(Committee committee)
  {
    committeesAdded.add(committee);
    super.addCommittee(committee);
  }
}
