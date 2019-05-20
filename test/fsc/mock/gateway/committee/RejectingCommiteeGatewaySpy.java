package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

import java.util.List;

public class RejectingCommiteeGatewaySpy extends RejectingCommitteeGatewayStub {
  public List<String> committeeNamesRequested;
  public List<Committee> committeesAdded;

  @Override
  public Committee getCommitteeFromCommitteeName(String name) throws CommitteeGateway.NoCommitteeWithThatNameException
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
