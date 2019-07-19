package fsc.mock.gateway.committee;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;

import java.util.List;

public class AcceptingCommitteeGatewaySpy implements CommitteeGateway {
  private static final long SEAT_ID = 2;
  public String submittedCommitteeName = null;
  public Committee returnedCommittee = null;

  public List<Committee> getCommittees() {
    return null;
  }

  public Committee getCommittee(String name) {
    submittedCommitteeName = name;
    returnedCommittee = new CommitteeStub(name, "Description");
    return returnedCommittee;
  }

  public Seat getSeat(String committeeName, String seatName) {
    Committee committee = getCommittee(committeeName);
    Seat seat = new Seat(seatName, Query.always(), committee);
    seat.setId(SEAT_ID);
    return seat;
  }

  @Override
  public void addCommittee(Committee committee) {
  }

  public void save() { }

  public boolean hasCommittee(String name) {
    submittedCommitteeName = name;
    return true;
  }

  public void addSeat(Seat seat) {

  }

  private class CommitteeStub extends Committee {

    private EntityFactory entityFactory = new SimpleEntityFactory();

    CommitteeStub(String name, String description) {
      super(name, description, Query.always());
    }

    public Seat getSeat(String seatName) {
      return entityFactory.createSeat(seatName, Query.always(), returnedCommittee);
    }
  }
}
