package fsc.mock;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

import java.util.ArrayList;
import java.util.List;

public class CommitteeGatewaySpy implements CommitteeGateway {

  private List<Committee> committees = new ArrayList<>();
  public String submittedCommitteeName;

  public CommitteeGatewaySpy(){}

  public Committee getCommitteeFromCommitteeName(String name) throws UnknownCommitteeException {
    submittedCommitteeName = name;
    throw new UnknownCommitteeException();
  }

  public void addCommittee(Committee committee) {
    committees.add(committee);
  }

  public void save() {}
}
