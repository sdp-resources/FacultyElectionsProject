package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

import java.util.Collection;
import java.util.List;

public class RejectingCommitteeGatewaySpy implements CommitteeGateway {
  public String submittedCommitteeName = null;
  public Committee committeeAdded = null;
  public boolean hasSaved = false;

  public List<Committee> getCommittees() {
    return null;
  }

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

  public Collection<Committee> getAllCommittees() {
    return null;
  }

}
