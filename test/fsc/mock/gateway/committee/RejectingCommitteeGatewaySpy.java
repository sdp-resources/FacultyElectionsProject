package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;

import java.util.List;

public class RejectingCommitteeGatewaySpy implements CommitteeGateway {
  public String submittedCommitteeName = null;
  public Committee committeeAdded = null;
  public boolean hasSaved = false;
  public Long submittedCommitteeId;

  public List<Committee> getCommittees() {
    return null;
  }

  @Override
  public Committee getCommitteeByName(String name) throws UnknownCommitteeException {
    submittedCommitteeName = name;
    throw new UnknownCommitteeException();
  }

  public Committee getCommittee(Long id) throws UnknownCommitteeException {
    submittedCommitteeId = id;
    throw new UnknownCommitteeException();
  }

  public Seat getSeat(String committeeName, String seatName) throws UnknownCommitteeException {
    Committee committee = getCommitteeByName(committeeName);
    return new Seat(seatName, Query.always(), committee);

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

  public void addSeat(Seat seat) { }

}
