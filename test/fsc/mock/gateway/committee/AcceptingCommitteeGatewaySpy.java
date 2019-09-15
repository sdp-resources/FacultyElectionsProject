package fsc.mock.gateway.committee;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.mock.EntityStub;

import java.util.List;

public class AcceptingCommitteeGatewaySpy implements CommitteeGateway {
  private static final long SEAT_ID = 2;
  public String submittedCommitteeName = null;
  public Committee returnedCommittee = null;
  public Long submittedCommitteeId = null;
  public Seat returnedSeat = null;

  public List<Committee> getCommittees() {
    return null;
  }

  public Committee getCommitteeByName(String name) {
    submittedCommitteeName = name;
    returnedCommittee = new CommitteeStub(name);
    return returnedCommittee;
  }

  public Committee getCommittee(Long id) {
    submittedCommitteeId = id;
    returnedCommittee = new CommitteeStub(id, "stubName");
    return returnedCommittee;
  }

  public Seat getSeat(Long seatId) throws UnknownSeatNameException {
    returnedSeat = EntityStub.seat();
    returnedSeat.setId(seatId);
    return returnedSeat;
  }

  public Seat getSeatByCommitteeAndSeatName(String committeeName, String seatName) {
    Committee committee = getCommitteeByName(committeeName);
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

    CommitteeStub(String name) {
      super(name, "Description", Query.always());
    }

    public CommitteeStub(long id, String name) {
      super(name, "Description", Query.always());
      setId(id);
    }

    public Seat getSeat(String seatName) {
      return entityFactory.createSeat(seatName, Query.always(), returnedCommittee);
    }
  }
}
