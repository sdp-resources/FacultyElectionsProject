package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

import java.util.List;

public class ProvidedCommitteeGatewaySpy implements CommitteeGateway {
  public String submittedCommitteeName = null;
  public Committee storedCommittee;
  public boolean saveWasCalled = false;

  public ProvidedCommitteeGatewaySpy(Committee committee) {
    storedCommittee = committee;
  }

  public List<Committee> getCommittees() {
    return null;
  }

  public Committee getCommittee(String name) {
    submittedCommitteeName = name;
    return storedCommittee;
  }

  @Override
  public void addCommittee(Committee committee) {
  }

  public void save() {
    if (submittedCommitteeName != null) { saveWasCalled = true; }
  }

  public boolean hasCommittee(String name) {
    submittedCommitteeName = name;
    return name.equals(storedCommittee.getName());
  }

}
