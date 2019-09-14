package fsc.mock.gateway.committee;

import fsc.entity.Committee;
import fsc.entity.Seat;
import fsc.gateway.CommitteeGateway;

import java.util.List;

public class ProvidedCommitteeGatewaySpy implements CommitteeGateway {
  public String submittedCommitteeName = null;
  public Committee storedCommittee;
  public boolean saveWasCalled = false;
  public Long submittedCommitteeId;

  public ProvidedCommitteeGatewaySpy(Committee committee) {
    storedCommittee = committee;
  }

  public List<Committee> getCommittees() {
    return null;
  }

  public Committee getCommitteeByName(String name) {
    submittedCommitteeName = name;
    return storedCommittee;
  }

  public Committee getCommittee(Long id) {
    submittedCommitteeId = id;
    return storedCommittee;
  }

  public Seat getSeat(String committeeName, String seatName) throws UnknownSeatNameException {
    Committee committee = getCommitteeByName(committeeName);
    return committee.getSeat(seatName);
  }

  @Override
  public void addCommittee(Committee committee) {
  }

  public void save() {
    if (submittedCommitteeName != null || submittedCommitteeId != null) { saveWasCalled = true; }
  }

  public boolean hasCommittee(String name) {
    submittedCommitteeName = name;
    return name.equals(storedCommittee.getName());
  }

  public void addSeat(Seat seat) { }

}
