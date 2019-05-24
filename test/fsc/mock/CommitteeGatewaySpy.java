package fsc.mock;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

import java.util.ArrayList;

public class CommitteeGatewaySpy implements CommitteeGateway {

  public static String submittedCommitteeName;

  public CommitteeGatewaySpy(){

  }

  public Committee getCommitteeFromCommitteeName(String name) throws UnknownCommitteeException {
    submittedCommitteeName = name;
    throw new UnknownCommitteeException();
  }

  public void addCommittee(Committee committee) {}

  public void save() {

  }
}
