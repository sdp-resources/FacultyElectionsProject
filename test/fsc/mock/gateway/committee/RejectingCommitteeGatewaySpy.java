package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

public class RejectingCommitteeGatewaySpy implements CommitteeGateway {
  public String submittedCommitteeName = null;
  public Committee committeeAdded = null;
  public boolean hasSaved = false;

  @Override
  public Committee getCommittee(String name) throws UnknownCommitteeException {
    submittedCommitteeName = name;
    throw new UnknownCommitteeException();
  }

  @Override
  public void addCommittee(Committee committee) {
    committeeAdded = committee;
  }

  public void save() {
    if (committeeAdded != null) { hasSaved = true; }
  }

  public boolean hasCommittee(String name) {
    submittedCommitteeName = name;
    return false;
  }

}
