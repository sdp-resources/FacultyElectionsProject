package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.entity.Seat;
import fsc.entity.query.TrueQuery;

public class AcceptingCommitteeGatewaySpy extends CommitteeGatewayDummy {
  public String committeeNameRequested = null;
  private Committee committeeAdded = null;
  public Committee returnedCommittee = null;

  public Committee getCommitteeFromCommitteeName(String name) {
    committeeNameRequested = name;
    returnedCommittee = new CommitteeStub(name, "Description");
    return returnedCommittee;
  }

  @Override
  public void addCommittee(Committee committee) {
    committeeAdded = committee;
  }

  private class CommitteeStub extends Committee {
    CommitteeStub(String name, String description) {
      super(name, description);
    }

    public Seat getSeat(String seatName) {
      return new Seat(seatName, new TrueQuery());
    }
  }
}
