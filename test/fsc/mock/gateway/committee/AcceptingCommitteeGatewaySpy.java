package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.entity.Seat;
import fsc.entity.query.TrueQuery;
import fsc.gateway.CommitteeGateway;

public class AcceptingCommitteeGatewaySpy implements CommitteeGateway {
  public String submittedCommitteeName = null;
  public Committee returnedCommittee = null;

  public Committee getCommitteeFromCommitteeName(String name) {
    submittedCommitteeName = name;
    returnedCommittee = new CommitteeStub(name, "Description");
    return returnedCommittee;
  }

  @Override
  public void addCommittee(Committee committee) {
  }

  public void save() { }

  private class CommitteeStub extends Committee {
    CommitteeStub(String name, String description) {
      super(name, description);
    }

    public Seat getSeat(String seatName) {
      return new Seat(seatName, new TrueQuery());
    }
  }
}
